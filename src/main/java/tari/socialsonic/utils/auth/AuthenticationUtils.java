package tari.socialsonic.utils.auth;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApiKeyService apiKeyService;
    /**
     *
     * @param params the parameters from the endpoint
     * @return a boolean indicating if the user is authenticated or not
     */
    public boolean authenticate(Map<String,String> params){
        // TODO, find more elegant (and possibly more performant?) solution
        if (params.containsKey("apiKey")){
            return validateApiKey(params.get("apiKey"));
        }
        else if(params.containsKey("u")){
            if (params.containsKey("p")){

                return !params.containsKey("apiKey"); // TODO, should return an error message in this case, should be moved somewhere else?
            }
            else if (params.containsKey("t") && params.containsKey("s")){

                byte[] salt = DigestUtils.md5Digest(params.get("s").getBytes());
                byte[] token = DigestUtils.md5Digest(params.get("t").getBytes());
                byte[] combination = ArrayUtils.mergeByteArrays(salt,token);
                return checkSaltedPassword(combination);
            }
        }
        return false;
    }

    public static boolean checkSaltedPassword(byte[] saltedPassword){
        return true;
    }

    public static String generateApiKey(){
        Random random = new Random();
        return Long.toHexString(random.nextLong());
    }
    public String generateApiKey(User user){
        ApiKey apikey = new ApiKey(user);
        apiKeyService.saveKey(apikey);
        return apikey.getKey();
    }

    public boolean validateApiKey(String apiKey){
        ApiKey tmpKey = apiKeyService.getApiKeyByKey(apiKey);
        return tmpKey.valid();
    }

}
