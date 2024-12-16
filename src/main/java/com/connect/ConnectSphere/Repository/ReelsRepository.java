package com.connect.ConnectSphere.Repository;

import com.connect.ConnectSphere.model.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels,Long> {
    public List<Reels> findByUserId(Long userId) throws Exception;
}
