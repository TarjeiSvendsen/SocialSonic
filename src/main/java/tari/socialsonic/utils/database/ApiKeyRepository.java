package tari.socialsonic.utils.database;

import org.springframework.data.jpa.repository.JpaRepository;
import tari.socialsonic.entities.ApiKey;
import tari.socialsonic.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey,String> {
    List<ApiKey> getAllByIssuedBy(User issuedBy);
    List<ApiKey> getApiKeysByDateIssued(LocalDate dateIssued);

    ApiKey getApiKeyByKey(String key);


}
