package com.bhumika.securecapita.entity;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class User {

    private Long id;
    @NotEmpty(message = "First name cannot be emapty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be emapty")
    private String lastName;
    @NotEmpty(message = "Email cannot be emapty")
    @Email(message = "Invalid email. Please check the email again.")
    private String email;
    @NotEmpty(message = "Password cannot be emapty")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;
}