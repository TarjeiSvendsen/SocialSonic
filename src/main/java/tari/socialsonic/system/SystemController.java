package tari.socialsonic.system;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tari.socialsonic.SubsonicResponse;
import tari.socialsonic.database.apiKey.ApiKeyService;
import tari.socialsonic.database.user.UserService;
import tari.socialsonic.database.user.User;
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.errors.ErrorCodeUtils;
import tari.socialsonic.utils.response.ResponseUtils;

import java.util.Map;

@RestController
public class SystemController {
    private final ResponseUtils responseUtils;
    private final AuthenticationUtils authUtils;
    private final ApiKeyService apiKeyService;
    private final UserService userService;
    // Not currently used, but can be useful for later...
    private final Environment environment;

    public SystemController(ResponseUtils responseUtils, AuthenticationUtils authUtils, ApiKeyService apiKeyService, UserService userService, Environment environment){
        this.responseUtils = responseUtils;
        this.authUtils = authUtils;
        this.apiKeyService = apiKeyService;
        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping(value = {"/rest/ping", "/api/v1/ping"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getPing(@RequestParam Map<String, String> params) {
        return ping(params);
    }

    @PostMapping(value={"/rest/ping","api/v1/ping"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> postPing(@RequestParam Map<String,String> body){
        return ping(body);
    }

    private ResponseEntity<String> ping(Map<String,String> body){
        int authResult = authUtils.authenticate(body);
        if (authResult == -1)
            return responseUtils.generateResponse(body);
        else return responseUtils.generateResponse(body, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }

    //
    @GetMapping(value= {"/rest/tokenInfo","/api/v1/tokenInfo"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getTokenInfo(@RequestParam Map<String, String> params){
        return tokenInfo(params);
    }

    @PostMapping(value={"/rest/tokenInfo","/api/v1/tokenInfo"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> postTokenInfo(@RequestParam Map<String,String> body){
        return tokenInfo(body);
    }

    private ResponseEntity<String> tokenInfo(Map<String,String> body){
        int authResult = authUtils.authenticate(body);
        
        if (authResult == -1) {
            SubsonicResponse response = new SubsonicResponse(true);
            SubsonicResponse tokenInfo = new SubsonicResponse(false);

            User owner = apiKeyService.getOwner(body.get("apiKey"));
            tokenInfo.addAttribute(new SubsonicResponse.Attribute("username", owner.getUserName()));

            response.addChildNode("tokenInfo", tokenInfo);

            return responseUtils.generateResponse(body, response);
        }
        else return responseUtils.generateResponse(body, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }
}
