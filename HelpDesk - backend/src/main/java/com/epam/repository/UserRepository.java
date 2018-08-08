package com.epam.repository;

import com.epam.entity.User;

public interface UserRepository {

    User getUserByEmail(String email);
}
