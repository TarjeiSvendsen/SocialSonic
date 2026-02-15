package tari.socialsonic.database.apiKey;

import org.springframework.data.jpa.repository.JpaRepository;
import tari.socialsonic.database.user.User;

import java.time.LocalDate;
import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey,String> {
    List<ApiKey> getAllByOwner(User owner);
    List<ApiKey> getApiKeysByDateIssued(LocalDate dateIssued);
    User getOwnerByKey(String key);
    ApiKey getApiKeyByKey(String key);


}
