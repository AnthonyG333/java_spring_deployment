package com.anthony.messagingapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anthony.messagingapp.models.Reply;
import com.anthony.messagingapp.repositories.ReplyRepository;

@Service
public class ReplyService {
	
	private final ReplyRepository replyRepo;
	
	public ReplyService(ReplyRepository replyRepo) {
		this.replyRepo = replyRepo;
	}
	
    public List<Reply> getAll() {
        return (List<Reply>) replyRepo.findAll();
    }

    public Reply create(Reply reply) {
        return replyRepo.save(reply);
    }

    public Reply getOne(Long id) {
        Optional<Reply> reply = replyRepo.findById(id);
        return reply.isPresent() ? reply.get() : null;
    }

    public Reply edit(Reply reply)  {
        return replyRepo.save(reply);
    }

    public void delete(Long id){
        replyRepo.deleteById(id);
    }

}
