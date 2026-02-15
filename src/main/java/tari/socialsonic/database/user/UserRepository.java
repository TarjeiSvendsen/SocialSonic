package tari.socialsonic.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User getUserById(int id);

    User getUserByUserName(String userName);
}
