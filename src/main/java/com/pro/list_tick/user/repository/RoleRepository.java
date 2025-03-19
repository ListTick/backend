package com.pro.list_tick.user.repository;

import com.pro.list_tick.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
