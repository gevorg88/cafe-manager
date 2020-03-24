package org.example.cafemanager.services.user.contracts;

import org.example.cafemanager.dto.user.UserPublicProps;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserUpdateRequestBody;

import java.util.Collection;

public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    Iterable<User> findAll();

    User createUser(CreateUserRequest createUserRequest, Role role) throws Exception;

    Collection<UserPublicProps> getAllWaiters();

    User save(User user);

    User getUserById(Long id);

    User update(Long id, UserUpdateRequestBody requestBody);

    void destroyUser(Long userId);
}
