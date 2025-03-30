package com.pro.list_tick.account.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Data
@Table(name = "account")
public class Account {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "roles", columnDefinition = "text[] default '{ROLE_USER}'")
    private List<String> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "username='" + username +
                ", email='" + email + '}';
    }

}
