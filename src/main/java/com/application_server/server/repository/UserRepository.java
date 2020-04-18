package com.application_server.server.repository;

import com.application_server.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    @Query("from User u join fetch u.role where u.email = :email")
    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}
