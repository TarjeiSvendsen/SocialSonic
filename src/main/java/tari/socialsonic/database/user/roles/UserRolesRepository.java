package tari.socialsonic.database.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tari.socialsonic.database.user.User;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Integer> {
    UserRoles getUserRolesByUser(User user);

}
