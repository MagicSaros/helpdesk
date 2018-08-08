package com.epam.service;

import com.epam.entity.User;

public interface UserService {

    User getUserByEmail(String email);
}
