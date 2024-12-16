package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.StoryRepository;
import com.connect.ConnectSphere.model.Story;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryService {
    @Autowired
    private UserService userService;
    @Autowired
    private StoryRepository storyRepository;
    public Story createStory(Story story, User user)
    {
        if (story.getCaption() == null || story.getImage() == null) {
            throw new RuntimeException("Caption or Image cannot be null");
        }

        Story createdStory = new Story();
        createdStory.setCaption(story.getCaption());
        createdStory.setImage(story.getImage());
        createdStory.setUser(user);
        createdStory.setTimeStamp(LocalDateTime.now());

        return storyRepository.save(createdStory);
    }

    public List<Story> findStoryByUserId(Long userId)
    {
        User user=userService.getUserById(userId);
        if(user==null)
            throw new RuntimeException("User not exists");
        return storyRepository.findStoryByUserId(userId);
    }
}



