package com.anthony.messagingapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anthony.messagingapp.models.PrivateMessage;
import com.anthony.messagingapp.repositories.PrivateMessageRepository;

@Service
public class PrivateMessageService {
	
	private final PrivateMessageRepository privateMessageRepo;
	
	public PrivateMessageService(PrivateMessageRepository privateMessageRepo) {
		this.privateMessageRepo = privateMessageRepo;
	}
	
	 public List<PrivateMessage> getAll() {
         return (List<PrivateMessage>) privateMessageRepo.findAll();
     }

     public PrivateMessage create(PrivateMessage privateMessage) {
         return privateMessageRepo.save(privateMessage);
     }

     public PrivateMessage getOne(Long id) {
         Optional<PrivateMessage> privateMessage = privateMessageRepo.findById(id);
         return privateMessage.isPresent() ? privateMessage.get() : null;
     }

     public PrivateMessage edit(PrivateMessage privateMessage)  {
         return privateMessageRepo.save(privateMessage);
     }

     public void delete(Long id){
         privateMessageRepo.deleteById(id);
     }
     
     public List<PrivateMessage> getByRecipientUserName(String userName) {
    	 return privateMessageRepo.findByRecipientUserName(userName);
     }
     
     
//     public List<PrivateMessage> getAllBetweenTwoUsers(Long userId, Long recipientId){
//    	 List<Object[]> table = privateMessageRepo.joinPrivateMessagesAndUser(userId, recipientId);
//    	 List<PrivateMessage> result = new ArrayList<PrivateMessage>();
//    	 
//    	 for(Object[] row : table) {
//    		 PrivateMessage privateMessage = (PrivateMessage) row[0];
//    		 result.add(privateMessage);
//    	 }
//    	 
//    	 return result;
//     }
     
}
