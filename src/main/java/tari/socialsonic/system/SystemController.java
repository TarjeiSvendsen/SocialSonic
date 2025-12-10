package tari.socialsonic.system;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.errors.ErrorCodes;
import tari.socialsonic.utils.response.ResponseUtils;

import java.util.Map;

@RestController
public class SystemController {
    private final ResponseUtils responseUtils;
    private final AuthenticationUtils authUtils;

    public SystemController(ResponseUtils responseUtils, AuthenticationUtils authUtils){
        this.responseUtils = responseUtils;
        this.authUtils = authUtils;
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
        else return responseUtils.generateResponse(body, ErrorCodes.createErrorResponseFromCode(authResult));
    }
}
