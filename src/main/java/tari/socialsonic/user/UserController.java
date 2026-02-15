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
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.auth.UserUtils;
import tari.socialsonic.utils.errors.ErrorCodes;
import tari.socialsonic.utils.response.ResponseUtils;

import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private final ResponseUtils responseUtils;
    private final AuthenticationUtils authUtils;
    private final ApiKeyService apiKeyService;
    private final UserService userService;
    // Not currently used, but can be useful for later...
    private final Environment environment;

    public UserController(ResponseUtils responseUtils, AuthenticationUtils authUtils, ApiKeyService apiKeyService, UserService userService, Environment environment) {
        this.responseUtils = responseUtils;
        this.authUtils = authUtils;
        this.apiKeyService = apiKeyService;
        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping(value = {"/rest/createUser", "/api/v1/createUser"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getCreateUser(@RequestParam Map<String, String> params) {
        return createUser(params);
    }

    private ResponseEntity<String> createUser(Map<String,String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult == -1) {
            boolean isUserAdmin = authUtils.isUserAdmin(params);
            if ( !isUserAdmin && Objects.equals(environment.getProperty("SOCIALSONIC_NON_ADMIN_USER_CREATION"),"false"))
                return responseUtils.generateResponse(params, ErrorCodes.createErrorResponseFromCode(50));
            User newUser = new User();
            for (String key: params.keySet()){
                switch (key){
                    case "username":
                        newUser.setUserName(params.get(key));
                        break;
                    case "password":
                        // TODO, add method to directly add password
                        break;
                    case "email":
                        newUser.setEmail(params.get(key));
                        break;
                    case "ldapAuthenticated":
                        // TODO, should be handled differently, as this can currently just be manually specified.
                        if (Objects.equals(params.get(key), "true"))
                            newUser.setLdapAuthenticated(true);
                        break;
                    case "adminRole":
                        if(!isUserAdmin && params.get(key).equals("true")) // Admins can only create new admins.
                            return responseUtils.generateResponse(params, ErrorCodes.createErrorResponseFromCode(50));
                        else UserUtils.setRoleStatus(newUser,"adminRole", isUserAdmin);
                        break;
                    default:
                        break;
                }
            }
            userService.save(newUser);
            return responseUtils.generateResponse(params, new SubsonicResponse(true));
        }
        else return responseUtils.generateResponse(params, ErrorCodes.createErrorResponseFromCode(authResult));
    }
}
