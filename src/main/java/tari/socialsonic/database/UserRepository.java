package tari.socialsonic.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tari.socialsonic.database.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User getUserById(int id);

    User getUserByUserName(String userName);
}
