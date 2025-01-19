

package com.connect.ConnectSphere.Controller;

import com.connect.ConnectSphere.Service.MessageService;
import com.connect.ConnectSphere.Service.UserService;
import com.connect.ConnectSphere.model.Message;
import com.connect.ConnectSphere.model.User;
import com.connect.ConnectSphere.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    // Create a new message in a chat
    @PostMapping("/messages/chat/{chatId}")
    public ResponseEntity<Response<Message>> createMessage(
            @RequestBody Message req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long chatId) {
        try {
            // Find user by JWT
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Create message in the chat
            Message createdMessage = messageService.createMessage(user, chatId, req);

            // Return success response
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("Message sent successfully", "success", createdMessage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }

    // Get all messages from a chat
    @GetMapping("/messages/chat/{chatId}")
    public ResponseEntity<Response<List<Message>>> findChatsMessages(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long chatId) {
        try {
            // Find user by JWT
            User user = userService.findUserByJwt(jwt);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                        new Response<>("Invalid or expired JWT", "error", null));
            }

            // Get messages from the chat
            List<Message> messages = messageService.findChatsMessages(chatId);

            // Return success response
            return ResponseEntity.ok(new Response<>("Messages fetched successfully", "success", messages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Response<>(e.getMessage(), "error", null));
        }
    }
}
