package org.cooksApp.service.user;


import lombok.RequiredArgsConstructor;
import org.cooksApp.model.User;
import org.cooksApp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserServie{

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getUsername();
        }
        return "";
    }
}
