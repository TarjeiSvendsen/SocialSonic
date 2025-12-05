package tari.socialsonic.system;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.errors.ErrorCodes;
import tari.socialsonic.utils.response.ResponseUtils;

import java.util.Map;

@RestController
public class SystemController {
    ResponseUtils responseUtils = new ResponseUtils();
    AuthenticationUtils authUtils = new AuthenticationUtils();

    @GetMapping(value = {"/rest/ping", "/api/v1/ping"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> ping(@RequestParam Map<String, String> params) {
        if (authUtils.authenticate(params))
            return responseUtils.generateResponse(params);
        else return responseUtils.generateResponse(params, ErrorCodes.createErrorResponse(ErrorCodes.INVALID_API_KEY));
    }

}
