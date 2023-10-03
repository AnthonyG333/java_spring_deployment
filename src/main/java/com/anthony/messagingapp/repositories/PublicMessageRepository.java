package com.anthony.messagingapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anthony.messagingapp.models.PublicMessage;


@Repository
public interface PublicMessageRepository extends CrudRepository<PublicMessage, Long>, PagingAndSortingRepository<PublicMessage, Long>{
	
	public List<PublicMessage> findByBodyContaining(String searchQuery);
	
	public List<PublicMessage> findAllByOrderByCreatedAtDesc();
	
	public List<PublicMessage> findTop5ByOrderByPopularityDesc();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE liaisonmessaging.public_messages SET popularity = popularity + 1 WHERE id = ?1", nativeQuery = true)
	public void incrementPopularity(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE liaisonmessaging.public_messages SET popularity = popularity - 1 WHERE id = ?1", nativeQuery = true)
	public void decrementPopularity(Long id);
	
		
}
