package tari.socialsonic.utils.errors;

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
}
