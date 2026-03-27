package tari.socialsonic.system.library;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tari.socialsonic.utils.auth.AuthenticationUtils;
import tari.socialsonic.utils.errors.ErrorCodeUtils;
import tari.socialsonic.utils.response.ResponseUtils;

import java.util.Map;

/**
 * This RestController is responsible for handling the calls for the startScan, and getScanStatus endpoint.
 */
@RestController
public class LibraryController {

    private final ResponseUtils responseUtils;
    private final AuthenticationUtils authUtils;

    public LibraryController(ResponseUtils responseUtils, AuthenticationUtils authUtils){
        this.responseUtils = responseUtils;
        this.authUtils = authUtils;
    }


    /**
     * This handles the GET request for this endpoint.
     * @param params the parameters passed with the request.
     * @return a {@code ResponseEntity<String>}.
     */
    @GetMapping(value = {"/rest/startScan"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getStartScan(@RequestParam Map<String, String> params) {
        return startScan(params);
    }

    @PostMapping(value = {"/rest/startScan"}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> postStartScan(@RequestParam Map<String, String> params) {
        return startScan(params);
    }

    private ResponseEntity<String> startScan(Map<String, String> params){
        int authResult = authUtils.authenticate(params);
        if (authResult <= -1) {
            if (!authUtils.isUserAuthorized(params) || !authUtils.isUserAdmin(params))
                // It is not specified in the OpenSubsonic docs,
                // but I assume non-admins shouldn't be able to initiate a media scan...
                return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(50));

            return responseUtils.generateResponse(params);
        }
        else return responseUtils.generateResponse(params, ErrorCodeUtils.createErrorResponseFromCode(authResult));
    }
}
