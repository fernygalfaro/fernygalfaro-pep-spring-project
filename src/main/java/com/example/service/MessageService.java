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
    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message){
        if(message.getMessageText().isBlank()|| message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Invalid");
        }
        if (!accountRepository.existsById(message.getPostedBy())){
            throw new IllegalArgumentException("Invalid");
        }
        return messageRepository.save(message);
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Message getMessageById(Integer id){
        return messageRepository.findById(id).orElse(null);
    }
    
    public int updateMessageText (Integer id, String newText){
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null){
            return 0;
        }
        if(newText == null || newText.trim().isEmpty() || newText.length() > 255){
            throw new IllegalArgumentException("Invalid");
        }
        message.setMessageText(newText);
        messageRepository.save(message);
        return 1;
    }
    public List<Message> getMessagesByUserId(Integer userId){
        return messageRepository.findByPostedBy(userId);
    }
    public int deleteMessage(Integer messageId){
        if(!messageRepository.existsById(messageId)){
            return 0;
        }
        messageRepository.deleteById(messageId);
        return 1;
    }


}
