package com.connect.ConnectSphere.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title should not exceed 255 characters")
    private String title;

    @Column(name = "video", nullable = false)
    @NotBlank(message = "Video URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*\\.(mp4|mpeg|mov|avi|webm|flv|wmv|mkv|3gp|ogv)$",
            message = "Invalid video URL. It must end with a valid video extension such as .mp4, .mpeg, .mov, .avi, .webm, etc.")
    private String video;

    @ManyToOne
    private User user;

}

