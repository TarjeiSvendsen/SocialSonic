package tari.socialsonic.utils.auth;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import tari.socialsonic.database.models.ApiKey;
import tari.socialsonic.database.ApiKeyService;
import tari.socialsonic.database.models.User;
import tari.socialsonic.user.RoleFunction;
import tari.socialsonic.utils.ArrayUtils;
import tari.socialsonic.database.UserService;

import java.util.Map;
import java.util.Random;

@Component
public class AuthenticationUtils {

    private final ApiKeyService apiKeyService;
    private final UserService userService;


    public AuthenticationUtils(final ApiKeyService apiKeyService,final UserService userService){
        this.apiKeyService = apiKeyService;
        this.userService = userService;
    }
    /**
     * Primary method for authenticating
     * @param params the parameters from the endpoint
     * @return an error code upon unsuccessfully authenticating, or -1 indicating success.
     */
    public int authenticate(Map<String,String> params){
        if (params.containsKey("apiKey")){
            if (params.containsKey("u")) return 43;
            return validateApiKey(params.get("apiKey")) ? -1 : 44;
        }
        else if(params.containsKey("u")){
            User tmpUser = userService.getUserByUsername(params.get("u"));
            if (tmpUser == null) return 40;
            if (params.containsKey("p")){
                return 42; // Plaintext password isn't secure, therefore it's not supported for now.
            }
            else if (params.containsKey("t") && params.containsKey("s")){
                byte[] combination = getTokenSaltCombination(params);
                return checkSaltedPassword(combination,tmpUser) ? -1 : 40;
            }
        }
        else return 10;
        return 0;
    }

    public boolean checkSaltedPassword(byte[] saltedPassword,User user){
        byte[] hashedPassword = user.getHashedPassword();
        return (hashedPassword == saltedPassword);
    }

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
     * @param user The {@link User} to check.
     * @return A boolean indicating whether the user has the admin role.
     */
    public boolean isUserAdmin(User user){
        return user.getUserRoleStatus("adminRole") == 1;
    }

    /**
     * Checks if user has the administrator role.
     * @param params The request params of the request.
     * @return A boolean indicating whether the user has the admin role.
     */
    public boolean isUserAdmin(Map<String,String> params){
        return isUserAdmin(getUserFromParams(params));
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

    public boolean validateApiKey(String apiKey){
        ApiKey tmpKey = apiKeyService.getApiKeyByKey(apiKey);
        if (tmpKey == null) return false;
        else return tmpKey.valid();
    }
    public byte[] getTokenSaltCombination(Map<String,String> params){
        byte[] salt = params.get("s").getBytes();
        byte[] token = params.get("t").getBytes();
        return DigestUtils.md5Digest(ArrayUtils.mergeByteArrays(salt,token));
    }
}
