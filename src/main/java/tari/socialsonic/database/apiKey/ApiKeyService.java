package tari.socialsonic.database.apiKey;

import org.springframework.stereotype.Service;
import tari.socialsonic.database.user.User;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApiKeyService{

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(final ApiKeyRepository apiKeyRepository){
        this.apiKeyRepository = apiKeyRepository;
    }

    public List<ApiKey> getAllByUser(User user){
        return apiKeyRepository.getAllByOwner(user);
    }
    public List<ApiKey> getAllByDateIssued(LocalDate date){
        return apiKeyRepository.getApiKeysByDateIssued(date);
    }
    public ApiKey getApiKeyByKey(String key){
        return apiKeyRepository.getApiKeyByKey(key);
    }
    public User getOwner(String key){return apiKeyRepository.getOwnerByKey(key);}

    public boolean saveKey(ApiKey key){
        apiKeyRepository.save(key);
        return true;
    }
}
