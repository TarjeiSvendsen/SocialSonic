package tari.socialsonic.utils.auth;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import tari.socialsonic.database.apiKey.ApiKey;
import tari.socialsonic.database.apiKey.ApiKeyService;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.UserService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class AuthenticationUtils {

    private final ApiKeyService apiKeyService;
    private final UserService userService;
    private final Environment environment;

    private HashMap<String,ApiKey> apiKeys = new HashMap<>();

    public AuthenticationUtils(final ApiKeyService apiKeyService, final UserService userService, Environment environment){
        this.apiKeyService = apiKeyService;
        this.userService = userService;
        this.environment = environment;
        regenerateApiKeyMap();
    }
    /**
     * Primary method for authenticating
     * @param params the parameters from the endpoint
     * @return an error code upon unsuccessfully authenticating, or -1 indicating success using u + p or -2 for api key.
     */
    public int authenticate(Map<String,String> params){
        if (params.containsKey("apiKey")){
            if (params.containsKey("u") || params.containsKey("t") || params.containsKey("s")) return 43;
            return validateApiKey(params.get("apiKey")) ? -2 : 44;
        }
        else if(params.containsKey("u")){
            User tmpUser = userService.getUserByUsername(params.get("u"));
            if (tmpUser == null) return 40;
            if (params.containsKey("p")){
                return comparePassword(params.get("p"), tmpUser) ? -1 : 40;
            }
            else if (params.containsKey("t") && params.containsKey("s")){
                // According to official spec, when api key based auth is available, s+t based auth should be deprecated.
                return 42;
            }
            else return 10;
        }
        else return 10;
    }

    /**
     * Compares the password sent by the user, to the one stored in the database.
     * @param password The password sent in with a request
     * @param user the user object retrieved from the database.
     * @return a boolean indicating if the passwords match or not.
     */
    private boolean comparePassword(String password,User user){
        byte[] sentPassword = hashPassword(user.getSalt(),password);
        return Arrays.equals(sentPassword, user.getHashedPassword());
    }
    /**
     * Hashes password using SHA-512 (TODO Will be bcrypt once refactor to spring security is done)
     * @param salt The salt to use.
     * @param passwordToHash the password to be hashed.
     * @return a byte array containing the hashed password.
     */
    public byte[] hashPassword(byte[] salt, String passwordToHash){
        MessageDigest md;
        try {
            // TODO, refactor to Spring Security, so bcrypt can be utilized.
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt);
        return md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Checks if a password is in clear text, as by the standard, encoded passwords should start with {@code enc: }
     * @return A boolean, yes if clear text, no if not.
     */
    private boolean passwordIsClearText(String password){
        return !password.startsWith("enc:");
    }

    /**
     * Generates a random key, ?? bytes long
     * @return a hexadecimal string.
     */
    public static String generateKey(){
        Random random = new Random();
        return Long.toHexString(random.nextLong());
    }
    public ApiKey generateApiKey(User user){
        ApiKey apikey = new ApiKey(user);
        if(!apiKeyService.saveKey(apikey)) throw new RuntimeException("Failed to save API-key, is the DB configured correctly?");
        return apikey;
    }

    /**
     * Checks if user has the administrator role.
     * @param params The request params of the request.
     * @return A boolean indicating whether the user has the admin role.
     */
    public boolean isUserAdmin(Map<String,String> params){
        return UserUtils.isUserAdmin(getUserFromParams(params));
    }

     /**
     * Gets the {@link User} object from the DB using the params from a request.
     * @param params The parameters used in a request, contains either username or apiKey field,
     * as this method should be used only after weeding out bad requests
     * containing none of the aforementioned values.
     * @return The {@link User} object.
     */
    private User getUserFromParams(Map<String,String> params){
        User tmpUser;
        if (params.containsKey("u")){
            tmpUser = userService.getUserByUsername(params.get("u"));
        }
        else{
            tmpUser = apiKeyService.getOwner(params.get("apiKey"));
        }
        return tmpUser;
    }

    /**
     * Refreshes the HashMap of API keys to ensure it stays up to date,
     * should be refreshed by methods updating the db.
     */
    public void regenerateApiKeyMap(){
        apiKeys.clear();
        for (ApiKey ak : apiKeyService.getAll()){
            apiKeys.put(ak.getKey(),ak);
        }
    }

    /**
     * Validates an API key by first checking the HashMap {@code apiKeys} which is expected to be up to date.
     * Endpoints updating the API keys in the DB
     * should call on {@code regenerateApiKeyMap} to make sure it stays up to date...
     * @param apiKey the {@link ApiKey}.key to validate.
     * @return a boolean indicating if the apiKey is valid or not.
     */
    public boolean validateApiKey(String apiKey){
        if (!apiKeys.containsKey(apiKey)) return false;
        return apiKeys.get(apiKey).valid();
    }
}
