package tari.socialsonic.database.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void removeUser(User user){
        userRepository.removeUserById(user.getId());
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }
}
