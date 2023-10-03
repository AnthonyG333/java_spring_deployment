package com.anthony.messagingapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anthony.messagingapp.models.Friend;
import com.anthony.messagingapp.repositories.FriendRepository;

@Service
public class FriendService {
	
	private final FriendRepository friendRepo;
	
	public FriendService(FriendRepository friendRepo) {
		this.friendRepo = friendRepo;
	}
	
	public List<Friend> getAll() {
        return (List<Friend>) friendRepo.findAll();
    }

    public Friend create(Friend friend) {
        return friendRepo.save(friend);
    }

    public Friend getOne(Long id) {
        Optional<Friend> friend = friendRepo.findById(id);
        return friend.isPresent() ? friend.get() : null;
    }

    public Friend edit(Friend friend)  {
        return friendRepo.save(friend);
    }

    public void delete(Long id){
        friendRepo.deleteById(id);
    }
    
}
