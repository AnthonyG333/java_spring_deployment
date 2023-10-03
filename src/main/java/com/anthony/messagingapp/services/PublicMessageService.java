package com.anthony.messagingapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anthony.messagingapp.models.PublicMessage;
import com.anthony.messagingapp.repositories.PublicMessageRepository;

@Service
public class PublicMessageService {
	
	private final PublicMessageRepository publicMessageRepo;
	
	private static final int PAGE_SIZE = 10;
	
	public PublicMessageService(PublicMessageRepository publicMessageRepo) {
		this.publicMessageRepo = publicMessageRepo;
	}
	
	public Page<PublicMessage> publicMessagesPerPage(int pageNumber){
		PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.DESC, "createdAt");
		Page<PublicMessage> publicMessages = publicMessageRepo.findAll(pageRequest);
		return publicMessages;
	}

    public PublicMessage create(PublicMessage publicMessage) {
        return publicMessageRepo.save(publicMessage);
    }

    public PublicMessage getOne(Long id) {
        Optional<PublicMessage> publicMessage = publicMessageRepo.findById(id);
        return publicMessage.isPresent() ? publicMessage.get() : null;
    }

    public PublicMessage edit(PublicMessage publicMessage)  {
        return publicMessageRepo.save(publicMessage);
    }

    public void delete(Long id){
        publicMessageRepo.deleteById(id);
    }
    
    public List<PublicMessage> searchByQuery(String searchQuery){
    		return 	publicMessageRepo.findByBodyContaining(searchQuery);
    }
    
    public List<PublicMessage> getPopularMessages(){
    		return publicMessageRepo.findTop5ByOrderByPopularityDesc();
    }
    
    public void incrementPopularity(Long id) {
    		publicMessageRepo.incrementPopularity(id);
    }
    
    public void decrementPopularity(Long id) {
    		publicMessageRepo.decrementPopularity(id);
    }
    
}
