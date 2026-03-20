package tari.socialsonic.utils.auth;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import tari.socialsonic.database.apiKey.ApiKey;
import tari.socialsonic.database.apiKey.ApiKeyService;
import tari.socialsonic.database.user.User;
import tari.socialsonic.database.user.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class AuthenticationUtils {

    private final ApiKeyService apiKeyService;
    private final UserService userService;
    private final Environment environment;

    private HashMap<String,ApiKey> apiKeys = new HashMap<>();
    private HashMap<String,User> users = new HashMap<>();

    public AuthenticationUtils(final ApiKeyService apiKeyService, final UserService userService, Environment environment){
        this.apiKeyService = apiKeyService;
        this.userService = userService;
        this.environment = environment;
        regenerateUserMap();
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
            User tmpUser = users.get(params.get("u"));
            if (tmpUser == null) return 40;
            if (params.containsKey("p")){
                return PasswordUtils.comparePassword(params.get("p"), tmpUser) ? -1 : 40;
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
     * Gets the {@link User} object from the HashMaps using the params from a request.
     * @param params The parameters used in a request, contains either username or apiKey field,
     * as this method should be used only after weeding out bad requests
     * containing none of the aforementioned values.
     * @return The {@link User} object.
     */
    public User getUserFromParams(Map<String,String> params){
        User tmpUser;
        if (params.containsKey("u")){
            tmpUser = users.get(params.get("u"));
        }
        else{
            tmpUser = apiKeys.get(params.get("apiKey")).getOwner();
        }
        return tmpUser;
    }

    public User getUserByUsername(String username){
        return users.getOrDefault(username, null);
    }
    /**
     * Regenerates the HashMap of existing users to ensure it stays up to date.
     * Should be called when updating the db in methods such as updateUser, createUser, or similar.
     */
    public void regenerateUserMap(){
        users.clear();
        for (User user : userService.getAll()){
            users.put(user.getUserName(),user);
        }
    }

    /**
     * Regenerates the HashMap of API keys to ensure it stays up to date.
     * Should be called when updating the db in methods and endpoints updating or creating/deleting API keys.
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
