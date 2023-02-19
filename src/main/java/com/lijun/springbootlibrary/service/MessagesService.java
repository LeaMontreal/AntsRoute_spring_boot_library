package com.lijun.springbootlibrary.service;

import com.lijun.springbootlibrary.dao.MessageRepository;
import com.lijun.springbootlibrary.entity.Message;
import com.lijun.springbootlibrary.requestmodels.AdminQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

  // TODO S28 51.2 save response and close question
  public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
    // find the message with id
    Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
    if (!message.isPresent()) {
      throw new Exception("Message not found");
    }

    message.get().setAdminEmail(userEmail);
    // take the response
    message.get().setResponse(adminQuestionRequest.getResponse());
    // change the state of the message, open to close
    message.get().setClosed(true);
    // save changed response into database
    messageRepository.save(message.get());
  }

}
