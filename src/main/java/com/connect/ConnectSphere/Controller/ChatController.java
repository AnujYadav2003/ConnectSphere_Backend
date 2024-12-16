//package com.connect.ConnectSphere.Controller;
//
//import com.connect.ConnectSphere.Service.ChatService;
//import com.connect.ConnectSphere.Service.UserService;
//import com.connect.ConnectSphere.model.Chat;
//import com.connect.ConnectSphere.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class ChatController {
//
//    @Autowired
//    private ChatService chatService;
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/api/chats")
//    public Chat createChat(
//            @RequestHeader("Authorization") String jwt,
//            @RequestBody ChatRequest req) throws Exception {
//        User reqUser = userService.findUserByJwt(jwt);
//        User user2 = userService.getUserById(req.getUserId());
//        return chatService.createChat(reqUser, user2);
//    }
//
//    @GetMapping("/api/chats")
//    public List<Chat> findUsersChat(@RequestHeader("Authorization")String jwt) throws Exception {
//        User user=userService.findUserByJwt(jwt);
//        List<Chat> chat = chatService.findUsersChat(user.getId());
//        return chat;
//    }
//}



package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.ChatService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Chat;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    // Create chat endpoint
    @PostMapping("/chats")
    public ResponseEntity<Response<Chat>> createChat(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ChatRequest req) {
        try {
            // Find user by JWT
            User reqUser = userService.findUserByJwt(jwt);
            if (reqUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Find the second user
            User user2 = userService.getUserById(req.getUserId());
            if (user2 == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new Response<>("User not found", "error", null));
            }

            // Create the chat
            Chat chat = chatService.createChat(reqUser, user2);

            // Return the success response with the created chat
            return ResponseEntity.ok(new Response<>("Chat created successfully", "success", chat));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Find user's chats endpoint
    @GetMapping("/chats")
    public ResponseEntity<Response<List<Chat>>> findUsersChat(@RequestHeader("Authorization") String jwt) {
        try {
            // Find user by JWT
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Fetch the user's chats
            List<Chat> chats = chatService.findUsersChat(user.getId());

            // Return the success response with the list of chats
            return ResponseEntity.ok(new Response<>("Chats fetched successfully", "success", chats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}
