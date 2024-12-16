package com.connect.ConnectSphere.Service;

import com.connect.ConnectSphere.Repository.ChatRepository;
import com.connect.ConnectSphere.Repository.MessageRepository;
import com.connect.ConnectSphere.model.Chat;
import com.connect.ConnectSphere.model.Message;
import com.connect.ConnectSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;

    public Message createMessage(User user, Long chatId, Message req) throws Exception{
        Chat chat=chatService.findChatById(chatId);

        Message msg=new Message();
        msg.setChat(chat);
        msg.setContent(req.getContent());
        msg.setImage(req.getImage());
        msg.setUser(user);
        msg.setTimestamp(LocalDateTime.now());
        Message savedMessage=messageRepository.save(msg);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return savedMessage;

    }
    public List<Message> findChatsMessages(Long chatId) throws Exception
    {
        Chat chat=chatService.findChatById(chatId);

        return messageRepository.findByChatId(chatId);
    }
}
