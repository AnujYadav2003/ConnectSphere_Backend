package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.ReelsRepository;
import com.connect.ConnectSphere.model.Reels;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsService {

    @Autowired
    private UserService userService;
    @Autowired
    private ReelsRepository reelsRepository;

    public Reels createReels(Reels reel, User user)
    {
        Reels createReel=new Reels();
        createReel.setTitle(reel.getTitle());
        createReel.setUser(user);
        createReel.setVideo(reel.getVideo());

        return reelsRepository.save(createReel);
    }


    public List<Reels> getAllReels()
    {
        return reelsRepository.findAll();

    }

    public List<Reels> getUserReels(Long userId) throws Exception
    {
        userService.getUserById(userId);
        return reelsRepository.findByUserId(userId);
    }
}

