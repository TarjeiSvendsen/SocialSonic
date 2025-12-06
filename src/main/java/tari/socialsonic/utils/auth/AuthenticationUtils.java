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
     *
     * @param params the parameters from the endpoint
     * @return a boolean indicating if the user is authenticated or not
     */
    public boolean authenticate(Map<String,String> params){
        // TODO, find more elegant (and possibly more performant?) solution
        if (params.containsKey("apiKey")){
            if (params.containsKey("u")) return false;
            return validateApiKey(params.get("apiKey"));
        }
        else if(params.containsKey("u")){
            if (params.containsKey("p")){
                return false; // Plaintext password isn't secure, therefore not supported.
            }
            else if (params.containsKey("t") && params.containsKey("s")){
                byte[] combination = getTokenSaltCombination(params);
                return checkSaltedPassword(combination);
            }
        }
        return false;
    }

    public static boolean checkSaltedPassword(byte[] saltedPassword){
        return true;
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
        byte[] salt = DigestUtils.md5Digest(params.get("s").getBytes());
        byte[] token = DigestUtils.md5Digest(params.get("t").getBytes());
        return ArrayUtils.mergeByteArrays(salt,token);
    }
}
