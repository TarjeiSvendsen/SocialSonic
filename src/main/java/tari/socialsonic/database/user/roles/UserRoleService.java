package tari.socialsonic.database.user.roles;

import org.springframework.stereotype.Service;
import tari.socialsonic.database.user.User;

@Service
public class UserRoleService {
    private final UserRolesRepository userRolesRepository;

    public UserRoleService(final UserRolesRepository userRoleRepository){
        this.userRolesRepository = userRoleRepository;
    }

    public UserRoles getUserRolesByUser(User user){
        return userRolesRepository.getUserRolesByUser(user);
    }

    public void save(UserRoles roles){
        userRolesRepository.save(roles);
    }
}
