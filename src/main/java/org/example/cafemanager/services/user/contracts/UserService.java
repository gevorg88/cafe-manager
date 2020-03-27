package org.example.cafemanager.services.user.contracts;

import org.example.cafemanager.dto.user.UserPublicProfile;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UpdateUserRequest;

import java.util.Collection;

public interface UserService {

    User findByEmail(String email);

    User findByUsernameOrEmail(String username, String email);

    Iterable<User> findAll();

    User createUser(CreateUserRequest createUserRequest, Role role) throws Exception;

    Collection<UserPublicProfile> getAllWaiters();

    User save(User user);

    User getUserById(Long id);

    User update(Long id, UpdateUserRequest requestBody);

    void destroyUser(Long userId);
}
