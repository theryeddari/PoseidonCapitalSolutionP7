package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", unique = true, nullable = false)
    private Byte id;


    @Size(max = 125)
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username", length = 125)
    private String username;

    @Size(max = 125)
    @Column(name = "password", length = 125)
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "{app.pattern.password}")
    private String password;

    @Size(max = 125)
    @Column(name = "fullname", length = 125)
    private String fullname;

    @Size(max = 125)
    @Column(name = "role", length = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

}
