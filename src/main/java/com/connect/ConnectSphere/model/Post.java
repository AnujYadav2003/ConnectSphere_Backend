package com.connect.ConnectSphere.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Post")
public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name="caption",nullable = false)
//    @JsonProperty("caption")
//    private String caption;
//
//    @Column(name = "imageurl")
//    @JsonProperty("imageurl")
//    private String imageurl;
//
//    @Column(name = "videourl")
//    @JsonProperty("videourl")
//    private String videourl;
//
//    @Column(name="createdAt")
//    private LocalDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="caption", nullable = false)
    @NotBlank(message = "Caption is required")
    @Size(max = 255, message = "Caption should not exceed 255 characters")
    @JsonProperty("caption")
    private String caption;

    @Column(name = "imageurl", nullable = false)
    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*\\.(jpg|jpeg|png|gif|bmp|webp|tiff|svg)$",
            message = "Invalid image URL. It must end with a valid image extension such as .jpg, .png, .gif, .webp, etc.")
    @JsonProperty("imageurl")
    private String imageurl;

    @Column(name = "videourl", nullable = false)
    @NotBlank(message = "Video URL is required")
    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*\\.(mp4|mpeg|mov|avi|webm|flv|wmv|mkv|3gp|ogv)$",
            message = "Invalid video URL. It must end with a valid video extension such as .mp4, .mpeg, .mov, .avi, .webm, etc.")
    @JsonProperty("videourl")
    private String videourl;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    //    one user --> many posts
    @ManyToOne
    private User user;
    //   one user--->many post ko like kar skta hai
    @OneToMany
    private List<User> liked =new ArrayList<>();
    @OneToMany
    private List<Comment>comments=new ArrayList<>();

}
