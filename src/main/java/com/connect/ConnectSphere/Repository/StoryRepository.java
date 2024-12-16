package com.connect.ConnectSphere.Repository;

import com.connect.ConnectSphere.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Long> {

    public List<Story> findStoryByUserId(Long userId);
}