package com.anthony.messagingapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anthony.messagingapp.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);
	
	void deleteById(Long id);
	
	List<User> findByUserNameContaining(String searchQuery);
	
}
