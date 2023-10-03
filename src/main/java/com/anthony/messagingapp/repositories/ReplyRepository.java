package com.anthony.messagingapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.messagingapp.models.Reply;

@Repository
public interface ReplyRepository extends CrudRepository<Reply, Long>{

}
