package org.bank.serviceaccount.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bank.serviceaccount.model.role.AccountRole;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Enter your password")
    @Column(name = "password")
    private String password;

    @Email(message = "Email syntax is not correct")
    @NotBlank(message = "Enter your email")
    @Column(name = "email")
    private String email;

    @Column(name = "registered")
    @CreationTimestamp
    private LocalDateTime registered;

    @Column(name = "status")
    private boolean enabled;

    @Column(name = "role")
    private AccountRole role;
}
