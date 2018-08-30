package com.epam.repository;

import com.epam.entity.User;
import com.epam.enums.UserRole;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    User addUser(User user);

    Optional<User> findOne(User user);

    List<User> getUsersByRole(UserRole role);
}
