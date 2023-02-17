package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.MessageRepository;
import com.lijun.springbootlibrary.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO 27 13.1 Create Message Service class and inject dependencies
@Service
@Transactional
public class MessagesService {
  private MessageRepository messageRepository;

  @Autowired
  public MessagesService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  // TODO 27 13.2 Implement postMessage() save user’s message (title + question) + user’s email into database messages table
  public void postMessage(Message messageRequest, String userEmail) {
    Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
    message.setUserEmail(userEmail);
    messageRepository.save(message);
  }
}
