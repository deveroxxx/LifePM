package bakos.life_pm.service;

import bakos.life_pm.entity.User;
import bakos.life_pm.exception.BusinessLogicRtException;
import bakos.life_pm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String userName, String password, String email) {
        if (userRepository.existsByUserName(userName)) {
            throw new BusinessLogicRtException("User already exists with name: " + userName);
        }
        if (userRepository.existsByEmail(email)) {
            throw new BusinessLogicRtException("User already exists with email: " + email);
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
    }
}
