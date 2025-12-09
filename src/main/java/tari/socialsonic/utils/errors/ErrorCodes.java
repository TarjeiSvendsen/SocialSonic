package tari.socialsonic.utils.errors;

import tari.socialsonic.SubsonicResponse;

public enum ErrorCodes {
    GENERIC(0,"A generic error."),
    MISSING_PARAMETER(10,"Required parameter is missing."),
    CLIENT_SUB_VERSION(20,"Incompatible Subsonic REST protocol version. Client must upgrade."),
    SERVER_SUB_VERSION(30,"Incompatible Subsonic REST protocol version. Server must upgrade."),
    AUTH_FAILED_CLASSIC(40,"Wrong username or password."),
    LDAP_TOKEN_NOT_SUPPORTED(41,"Token authentication not supported for LDAP users."),
    NON_SUPPORTED_AUTH(42,"Provided authentication mechanism not supported."),
    CONFLICTION_AUTHS(43,"Multiple conflicting authentication mechanisms provided."),
    INVALID_API_KEY(44,"Invalid API key."),
    NOT_AUTHORIZED(50,"User is not authorized for the given operation."),
    SUBSONIC_PREM(60,"The trial period for the Subsonic server is over. Please upgrade to Subsonic Premium. Visit subsonic.org for details."),
    NOT_FOUND(70,"The requested data was not found.");

    public final int code;
    public final String description;

    ErrorCodes(int code,String description) {
        this.code = code;
        this.description = description;
    }

    public static SubsonicResponse createErrorResponseFromCode(int code){
        SubsonicResponse response = new SubsonicResponse();
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
