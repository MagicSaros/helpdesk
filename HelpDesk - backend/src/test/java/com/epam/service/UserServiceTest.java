package com.epam.service;

import static org.junit.Assert.assertEquals;

import com.epam.entity.User;
import com.epam.enums.UserRole;
import com.epam.exception.UserNotFoundException;
import com.epam.repository.UserRepository;
import com.epam.service.implementation.UserServiceImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private List<User> users;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Before
    public void init() {
        users = new LinkedList<>();

        User manager = new User.Builder()
            .setId(1L)
            .setFirstName("Manager")
            .setLastName("Manager")
            .setRole(UserRole.MANAGER)
            .setEmail("manager@mail")
            .build();

        User employee = new User.Builder()
            .setId(1L)
            .setFirstName("Employee")
            .setLastName("Employee")
            .setRole(UserRole.EMPLOYEE)
            .setEmail("employee@mail")
            .build();

        users.add(manager);
        users.add(employee);
    }

    @Test
    public void getUserByEmailTest() {
        User user = users.get(0);
        String email = user.getEmail();

        BDDMockito.given(userRepository.getUserByEmail(email)).willReturn(Optional.ofNullable(user));

        User actual = userService.getUserByEmail(email);

        assertEquals(user, actual);
    }

    @After
    public void clear() {
        users.clear();
    }
}
