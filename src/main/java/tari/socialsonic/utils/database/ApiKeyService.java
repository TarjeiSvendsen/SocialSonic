package tari.socialsonic.utils.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tari.socialsonic.entities.ApiKey;
import tari.socialsonic.entities.User;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApiKeyService{

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public List<ApiKey> getAllByUser(User user){
        return apiKeyRepository.getAllByIssuedBy(user);
    }
    public List<ApiKey> getAllByDateIssued(LocalDate date){
        return apiKeyRepository.getApiKeysByDateIssued(date);
    }
    public ApiKey getApiKeyByKey(String key){
        return apiKeyRepository.getApiKeyByKey(key);
    }
    public boolean saveKey(ApiKey key){
        apiKeyRepository.save(key);
        return true;
    }
}
