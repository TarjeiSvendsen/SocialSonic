package tari.socialsonic.user;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tari.socialsonic.SubsonicResponse;
import tari.socialsonic.database.apiKey.ApiKeyService;
import tari.socialsonic.database.user.UserService;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.roles.UserRoleService;
import tari.socialsonic.utils.auth.AuthenticationUtils;
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
    // Not currently used, but can be useful for later...
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

    private ResponseEntity<String> createUser(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            boolean isUserAdmin = authUtils.isUserAdmin(params);
            if ( !isUserAdmin && Objects.equals(environment.getProperty("SCS_NON_ADMIN_USER_CREATION"),"false")
                    || !isUserAdmin && params.get("adminRole").equals("true")){
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));
            }
            if (!containsParams(params,new String[]{"username","password","email"}))
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(10));
            User newUser = new User();
            for (String key: params.keySet()){
                switch (key){
                    case "username":
                        newUser.setUserName(params.get(key));
                        break;
                    case "password":
                        newUser.setSalt(UserUtils.generateSalt());
                        newUser.setHashedPassword(authUtils.hashPassword(newUser.getSalt(), params.get(key)));
                        break;
                    case "email":
                        newUser.setEmail(params.get(key));
                        break;
                    case "ldapAuthenticated":
                        // TODO, should be handled differently, as this can currently just be manually specified.
                        if (Objects.equals(params.get(key), "true"))
                            newUser.setLdapAuthenticated(true);
                        break;
                    default:
                        break;
                }
            }
            newUser.setRoles(UserUtils.setRoles(params,isUserAdmin));
            userRoleService.save(newUser.getRoles());
            userService.save(newUser);
            return responseUtils.generateResponse(params, new SubsonicResponse(true));
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }

    private boolean containsParams(Map<String,String> params,String[] toMatch){
        for (String s: toMatch){
            if (!params.containsKey(s)) return false;
        }
        return true;
    }
}
