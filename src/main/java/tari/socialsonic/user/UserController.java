package tari.socialsonic.user;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tari.socialsonic.SubsonicResponse;
import tari.socialsonic.database.apiKey.ApiKeyService;
import tari.socialsonic.database.user.UserService;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoleService;
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.auth.PasswordUtils;
import tari.socialsonic.utils.auth.UserUtils;
import tari.socialsonic.utils.errors.ErrorCodeUtils;
import tari.socialsonic.utils.response.ResponseUtils;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private final ResponseUtils responseUtils;
    private final AuthenticationUtils authUtils;
    private final ApiKeyService apiKeyService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final Environment environment;

    public UserController(ResponseUtils responseUtils, AuthenticationUtils authUtils, ApiKeyService apiKeyService, UserRoleService userRoleService, UserService userService, Environment environment) {
        this.responseUtils = responseUtils;
        this.authUtils = authUtils;
        this.apiKeyService = apiKeyService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping(value = {"/rest/createUser", "/api/v1/createUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getCreateUser(@RequestParam Map<String, String> params) {
        return createUser(params);
    }

    @PostMapping(value = {"/rest/createUser", "/api/v1/createUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postCreateUser(@RequestParam Map<String, String> params) {
        return createUser(params);
    }

    private ResponseEntity<String> createUser(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            boolean isUserAdmin = authUtils.isUserAdmin(params);
            if ( !isUserAdmin && Objects.equals(environment.getProperty("SCS_NON_ADMIN_USER_CREATION"),"false")
                    || !isUserAdmin && params.get("adminRole").equals("true")){
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));
            }
            if (!responseUtils.containsParams(params,new String[]{"username","password","email"}))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
            User newUser = new User();
            UserUtils.handleUserParams(params,newUser);
            newUser.setRoles(UserUtils.setRoles(params,isUserAdmin));
            userRoleService.save(newUser.getRoles());
            userService.save(newUser);
            authUtils.regenerateUserMap();
            return responseUtils.generateResponse(params, new SubsonicResponse(true));
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }

    @GetMapping(value = {"/rest/deleteUser", "/api/v1/deleteUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getDeleteUser(@RequestParam Map<String, String> params) {
        return deleteUser(params);
    }

    @PostMapping(value = {"/rest/deleteUser", "/api/v1/deleteUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postDeleteUser(@RequestParam Map<String, String> params) {
        return deleteUser(params);
    }

    private ResponseEntity<String> deleteUser(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            if (!authUtils.isUserAuthorized(params))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));

            if (!responseUtils.containsParams(params, new String[]{"username"}))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
            User userToRemove = authUtils.getUserByUsername(params.get("username"));
            userService.removeUser(userToRemove);
            authUtils.regenerateUserMap();
            return responseUtils.generateResponse(params);
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }

    @GetMapping(value = {"/rest/updateUser", "/api/v1/updateUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getUpdateUser(@RequestParam Map<String, String> params) {
        return updateUser(params);
    }

    @PostMapping(value = {"/rest/updateUser", "/api/v1/updateUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postUpdateUser(@RequestParam Map<String, String> params) {
        return updateUser(params);
    }

    private ResponseEntity<String> updateUser(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            if (!authUtils.isUserAuthorized(params))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));
            if (!responseUtils.containsParams(params,new String[]{"username"}))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
            User user = authUtils.getUserByUsername(params.get("username"));
            UserUtils.handleUserParams(params,user);
            user.setRoles(UserUtils.setRoles(params,authUtils.isUserAdmin(params)));
            userRoleService.save(user.getRoles());
            userService.save(user);
            authUtils.regenerateUserMap();
            return responseUtils.generateResponse(params, new SubsonicResponse(true));
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }

    @GetMapping(value = {"/rest/getUser", "/api/v1/getUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getGetUser(@RequestParam Map<String, String> params) {
        // Sidenote, this method name sucks.
        return getUserInfo(params);
    }

    @PostMapping(value = {"/rest/getUser", "/api/v1/getUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postGetUser(@RequestParam Map<String, String> params) {
        return getUserInfo(params);
    }

    private ResponseEntity<String> getUserInfo(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            User authenticatedUser = authUtils.getUserFromParams(params);
            if (!authenticatedUser.getUserName().equals(params.get("username")) || !authUtils.isUserAdmin(params)){
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));
            }
            if (!responseUtils.containsParams(params,new String[]{"username"}))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
            User user = authUtils.getUserByUsername(params.get("username"));
            return responseUtils.generateResponse(params,
                    new SubsonicResponse("user",
                            UserUtils.convertUserToSubsonicResponse(user)));
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }
    

    @GetMapping(value = {"/rest/updatePassword", "/api/v1/updatePassword"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getUpdatePassword(@RequestParam Map<String, String> params) {
        return updatePassword(params);
    }
    @PostMapping(value = {"/rest/updatePassword", "/api/v1/updatePassword"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postUpdatePassword(@RequestParam Map<String, String> params) {
        return updatePassword(params);
    }

    private ResponseEntity<String> updatePassword(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (!responseUtils.containsParams(params,new String[]{"username","password"}))
            return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
        if (authResult > -1) return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));

        if (!authUtils.isUserAuthorized(params))
            return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));
        // updatedUser is the user to update the password for.
        User updatedUser = authUtils.getUserByUsername(params.get("username"));
        if (updatedUser == null) return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(70));

        else {
            updatedUser.setHashedPassword(PasswordUtils.hashPassword(updatedUser.getSalt(),params.get("password")));
            userService.save(updatedUser);
            authUtils.regenerateUserMap();
            return responseUtils.generateResponse(params);
        }
    }


}
