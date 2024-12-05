package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Message getMessageById(Integer id){
        return messageRepository.getById(id);
    }
    public void deleteMessageById(Integer id){
        messageRepository.deleteById(id);
    }
    public Message updateMessageText (Integer id, String newText){
        Message message = getMessageById(id);
        message.setMessageText(newText);
        return messageRepository.save(message);
    }
    public List<Message> getMessagesByUserId(Integer userId){
        return messageRepository.findByPostedBy(userId);
    }


}
