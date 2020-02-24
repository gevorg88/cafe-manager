package org.example.cafemanager.services.user.contracts;

import org.example.cafemanager.accessData.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;

public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    Iterable<User> findAll();

    User createUser(UserCreate userCreate, Role role) throws Exception;

    User save(User user);
}
