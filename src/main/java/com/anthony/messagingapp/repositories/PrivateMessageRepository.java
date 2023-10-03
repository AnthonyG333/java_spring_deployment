package com.anthony.messagingapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.messagingapp.models.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends CrudRepository<PrivateMessage, Long>{

	public List<PrivateMessage> findByRecipientUserName(String userName);
	
//	@Query("SELECT p, u FROM private_messages p LEFT JOIN p.user u WHERE u.id = ?1 AND p.recipient_id = ?2")
//	public List<Object[]> joinPrivateMessagesAndUser(Long userId, Long recipientId);
	
}
