package com.pro.list_tick.account.model.keycloak;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "user_entity")
public class KeycloakUser {

    @Id
    @Size(min = 36, max = 36, message = "Id must have exactly 36 characters.")
    private String id;

    @Email
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Boolean enabled;

    @Column(name = "realm_id")
    private String realmId;

    @Column(name = "created_timestamp")
    private Long createdTimestamp;

}
