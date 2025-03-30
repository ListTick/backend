package com.pro.list_tick.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @UuidGenerator
    private UUID id;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "username='" + username +
                ", email='" + email + '}';
    }

}
