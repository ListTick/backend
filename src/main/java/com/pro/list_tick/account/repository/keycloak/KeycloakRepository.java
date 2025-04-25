package com.pro.list_tick.account.repository.keycloak;

import com.pro.list_tick.account.model.keycloak.KeycloakUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KeycloakRepository extends JpaRepository<KeycloakUser, UUID> {
}
