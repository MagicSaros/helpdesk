package com.epam.service;

import com.epam.entity.User;
import com.epam.enums.UserRole;
import java.util.List;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id);

    User addUser(User user);

    User findOne(User user);

    List<User> getUsersByRole(UserRole role);
}
