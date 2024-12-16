package com.connect.ConnectSphere.Repository;

import com.connect.ConnectSphere.model.Chat;
import com.connect.ConnectSphere.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {


    public List<Chat> findByUsersId(Long userId);

    @Query("SELECT c FROM Chat c WHERE :user1 MEMBER OF c.users AND :user2 MEMBER OF c.users")
    Chat findChatByUserId(User user1, User user2);

}
