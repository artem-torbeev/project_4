package com.application_server.server.repository;

import com.application_server.server.model.Role;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findRoleById (Long id);
}
