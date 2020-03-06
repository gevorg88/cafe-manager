package org.example.cafemanager.services.user.contracts;

import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.dto.user.UserCreate;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;

import java.util.Collection;

public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    Iterable<User> findAll();

    User createUser(UserCreate userCreate, Role role) throws Exception;

    Collection<UserPublicProps> getAllWaiters();

    User save(User user);
}
