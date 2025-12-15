package KPODZ3.AnalisysService.Service;

import org.springframework.stereotype.Service;

import KPODZ3.AnalisysService.Model.File;
import KPODZ3.AnalisysService.Model.User;
import KPODZ3.AnalisysService.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrAddFiles(String username, File newFile) {
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User u = new User();
                    u.setUsername(username);
                    return u;
                });

        
        newFile.setUser(user);
        user.getFiles().add(newFile);
        

        return userRepository.save(user);
    }
}