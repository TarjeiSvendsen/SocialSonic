package tari.socialsonic.utils.auth;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import tari.socialsonic.entities.ApiKey;
import tari.socialsonic.utils.database.ApiKeyService;
import tari.socialsonic.entities.User;
import tari.socialsonic.utils.ArrayUtils;

import java.util.Map;
import java.util.Random;

@Component
public class AuthenticationUtils {

    private final ApiKeyService apiKeyService;

    public AuthenticationUtils(final ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
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
            if (params.containsKey("p")){
                return 42; // Plaintext password isn't secure, therefore it's not supported.
            }
            else if (params.containsKey("t") && params.containsKey("s")){
                byte[] combination = getTokenSaltCombination(params);
                return checkSaltedPassword(combination) ? -1 : 40;
            }
        }
        else return 10;
        return 0;
    }

    public static boolean checkSaltedPassword(byte[] saltedPassword){

        return false;
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
