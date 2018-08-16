package com.epam.repository;

import com.epam.entity.User;
import java.util.Optional;

public interface UserRepository {

    User getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    User addUser(User user);

    Optional<User> findOne(User user);
}
