package com.connect.ConnectSphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")) // Ensure unique email
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    @NotBlank(message = "First name is required")
    @JsonProperty("firstname")
    private String firstName;

    @Column(name = "lastname", nullable = false)
    @NotBlank(message = "Last name is required")
    @JsonProperty("lastname")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty("password")
    private String password;



    private List<Long> followers= new ArrayList<>();
    private List<Long> followings=new ArrayList<>();
    @JsonIgnore
    @ManyToMany
    private List<Post> savedPost = new ArrayList<>();

}




