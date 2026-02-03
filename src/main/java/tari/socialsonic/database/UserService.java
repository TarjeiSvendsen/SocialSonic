package tari.socialsonic.database;

import org.springframework.stereotype.Service;
import tari.socialsonic.database.models.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username){
        return userRepository.getUserByUserName(username);
    }
    public User getUserById(int id){
        return userRepository.getUserById(id);
    }

    public void save(User user){
        userRepository.save(user);
    }
}
