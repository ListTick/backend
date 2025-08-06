package com.pro.list_tick.account.repository.keycloak;

import com.pro.list_tick.account.model.keycloak.KeycloakUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KeycloakRepository extends JpaRepository<KeycloakUser, String> {

    @Query("SELECT ku.id FROM KeycloakUser ku WHERE ku.email = :email")
    Optional<String> findIdByEmail(String email);

    @Query("SELECT ku.email FROM KeycloakUser ku WHERE ku.id = :id")
    Optional<String> findEmailById(String id);
}
