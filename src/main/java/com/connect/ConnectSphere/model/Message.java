package com.connect.ConnectSphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "image", nullable = false)
    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*\\.(jpg|jpeg|png|gif|bmp|webp|tiff|svg)$",
            message = "Invalid image URL. It must end with a valid image extension such as .jpg, .png, .gif, .webp, etc.")
    private String image;

    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Chat chat;
    private LocalDateTime timestamp;


}
