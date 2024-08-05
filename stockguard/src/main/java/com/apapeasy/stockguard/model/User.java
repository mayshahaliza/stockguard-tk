package com.apapeasy.stockguard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotBlank(message = "Email must be filled.")
    @Email(message = "Email format must be valid.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Role must be filled.")
    @Column(name = "role", nullable = false)
    private String role;

    @NotBlank(message = "Username must be filled.")
    @Size(min = 1, max = 255, message = "Length username must be between 1--255 character.")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password must be filled.")
    @Size(min = 8, message = "Password minimal 8 character.")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Notifikasi> notifikasi;
}