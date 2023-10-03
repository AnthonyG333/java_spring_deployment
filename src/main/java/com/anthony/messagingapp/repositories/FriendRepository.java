package com.anthony.messagingapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.anthony.messagingapp.models.Friend;

public interface FriendRepository extends CrudRepository<Friend, Long> {

}
