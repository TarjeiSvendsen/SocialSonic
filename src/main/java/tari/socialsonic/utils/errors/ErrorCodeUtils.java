package tari.socialsonic.utils.errors;

import tari.socialsonic.SubsonicResponse;

import static tari.socialsonic.utils.errors.ErrorCodes.*;

public class ErrorCodeUtils {
    /**
     * Creates an error response.
     * @param code the code to construct the error node from.
     * @return a {@link SubsonicResponse} with a child error node containing the error details.
     */
    public static SubsonicResponse createErrorResponseFromCode(int code){
        SubsonicResponse response = new SubsonicResponse(true);
        response.overrideAttribute("status","failed");
        switch (code){
            case 10 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(MISSING_PARAMETER.code, MISSING_PARAMETER.description, "https://example.com")));
                return response;
            }
            case 20 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(CLIENT_SUB_VERSION.code, CLIENT_SUB_VERSION.description, "https://example.com")));
                return response;
            }
            case 30 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(SERVER_SUB_VERSION.code, SERVER_SUB_VERSION.description, "https://example.com")));
                return response;
            }
            case 40 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(AUTH_FAILED_CLASSIC.code, AUTH_FAILED_CLASSIC.description, "https://example.com")));
                return response;
            }
            case 41 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(LDAP_TOKEN_NOT_SUPPORTED.code, LDAP_TOKEN_NOT_SUPPORTED.description, "https://example.com")));
                return response;
            }
            case 42 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(NON_SUPPORTED_AUTH.code, NON_SUPPORTED_AUTH.description, "https://example.com")));
                return response;
            }
            case 43 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(CONFLICTION_AUTHS.code, CONFLICTION_AUTHS.description, "https://example.com")));
                return response;
            }
            case 44 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(INVALID_API_KEY.code, INVALID_API_KEY.description, "https://example.com")));
                return response;
            }
            case 50 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(NOT_AUTHORIZED.code, NOT_AUTHORIZED.description, "https://example.com")));
                return response;
            }
            case 60 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(SUBSONIC_PREM.code, SUBSONIC_PREM.description, "https://example.com")));
                return response;
            }
            case 70 -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(NOT_FOUND.code, NOT_FOUND.description, "https://example.com")));
                return response;
            }
            default -> {
                response.addChildNode("error",constructErrorNode(constructErrorAttributes(GENERIC.code, GENERIC.description, "https://example.com")));
                return response;
            }
        }
    }

    public static SubsonicResponse.Attribute[] constructErrorAttributes(int code, String message, String helpUrl){
        return new SubsonicResponse.Attribute[]{new SubsonicResponse.Attribute("code",code),
                new SubsonicResponse.Attribute("message",message),
                new SubsonicResponse.Attribute("helpUrl",helpUrl)};
    }
    public static SubsonicResponse constructErrorNode(SubsonicResponse.Attribute[] attributes){
        return new SubsonicResponse(attributes);
    }
}
