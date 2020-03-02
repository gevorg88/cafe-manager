package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.User;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.dto.user.UserPublicProps;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository(value = "userRepo")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    Collection<UserPublicProps> findAllByRole(Role role);
}
