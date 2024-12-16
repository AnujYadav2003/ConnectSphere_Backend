package com.connect.ConnectSphere.Repository;

import com.connect.ConnectSphere.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}