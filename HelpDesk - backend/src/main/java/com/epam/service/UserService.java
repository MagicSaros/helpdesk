package com.epam.service;

import com.epam.entity.User;

public interface UserService {

    User getUserByEmail(String email);

    User getUserById(Long id);

    User addUser(User user);

    User findOne(User user);
}
