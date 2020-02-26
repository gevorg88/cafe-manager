package org.example.cafemanager.repositories;

import org.example.cafemanager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "userRepo")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findByUserName(String username);
}
