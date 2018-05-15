package com.helpmycity.repository;

import com.helpmycity.model.Role;
import com.helpmycity.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByRole(RoleName role);

}