package com.example.controller;

import javax.net.ssl.HttpsURLConnection;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        try{
            Account created = accountService.registerAccount(account);
            return ResponseEntity.ok(created);
        }catch(IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        try{
            return ResponseEntity.ok(accountService.login(account.getUsername(), account.getPassword()));
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        try{
            return ResponseEntity.ok(messageService.createMessage(message));
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/messages")
    public List<Message> getAllMessages (){
        return messageService.getAllMessages();
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id){
      Message message = messageService.getMessageById(id);
      if(message == null){
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.ok(message);
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody String newText){
        try {
            if(newText.isBlank() || newText.length() > 255){
                return ResponseEntity.badRequest().body(null);
            }
            int rowsUpdated = messageService.updateMessageText(messageId, newText);
            if(rowsUpdated == 1){
                return ResponseEntity.ok(rowsUpdated);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    } 
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessageByUser(@PathVariable Integer accountId){
        return messageService.getMessagesByUserId(accountId);
    }   
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){
       
           int rowsUpdated = messageService.deleteMessage(messageId);
            if(rowsUpdated == 0){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.ok(rowsUpdated);
       
    }
}
