package com.connect.ConnectSphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", nullable = false)
    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*\\.(jpg|jpeg|png|gif|bmp|webp|tiff|svg)$",
            message = "Invalid image URL. It must end with a valid image extension such as .jpg, .png, .gif, .webp, etc.")
    private String image;

    @Column(name = "caption", nullable = false)
    @NotBlank(message = "Caption is required")
    @Size(max = 255, message = "Caption should not exceed 255 characters")
    private String caption;

    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;
    @ManyToOne
    private User user;


}