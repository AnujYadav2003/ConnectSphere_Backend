package com.connect.ConnectSphere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Chat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String chatName;

    private String chatImage;
    @ManyToMany
    private List<User> users=new ArrayList<>();

    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "chat")
    private List<Message>messages=new ArrayList<>();


}
