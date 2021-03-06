package com.epam.service.implementation;

import com.epam.entity.User;
import com.epam.enums.UserRole;
import com.epam.exception.UserNotFoundException;
import com.epam.repository.UserRepository;
import com.epam.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User does not exist"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getUserById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found by passed id"));
    }

    @Override
    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public User findOne(User user) {
        return userRepository.findOne(user)
            .orElseThrow(() -> new UserNotFoundException("User is not exist"));
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.getUsersByRole(role);
    }
}
