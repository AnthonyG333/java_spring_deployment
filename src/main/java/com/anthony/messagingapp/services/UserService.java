package com.anthony.messagingapp.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.anthony.messagingapp.models.LoginUser;
import com.anthony.messagingapp.models.User;
import com.anthony.messagingapp.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	
	public UserService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	 public User createUser(User user) {
         String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
         user.setPassword(hashedPassword);


         String hashedConfirmPassword = BCrypt.hashpw(user.getConfirmPassword(), BCrypt.gensalt());
         user.setConfirmPassword(hashedConfirmPassword);
         
         return userRepo.save(user);
     }
	 
     public User getById(Long id) {
         Optional<User> user = userRepo.findById(id);
         return user.isPresent() ? user.get() : null;
     }

     public User getByEmail(String email) {
         Optional<User> potentialUser = userRepo.findByEmail(email);
         return potentialUser.isPresent() ? potentialUser.get() : null;
     }


     public User login(LoginUser loginUser, BindingResult result) {
         if (result.hasErrors()) {
         return null; 
         }
         User existingUser = this.getByEmail(loginUser.getEmail());
         if (existingUser == null) {
         result.rejectValue("email", "Unique", "Invalid credentials.");
         return null; 
         }
         if (!BCrypt.checkpw(loginUser.getPassword(), existingUser.getPassword())) {
         result.rejectValue("email", "Unique", "Invalid credentials.");
         }
         return existingUser; 
     }
     
     public User saveImage(User user) {
    	 	return userRepo.save(user);
     }
     
     public List<User> searchByQuery(String searchQuery){
    	 return userRepo.findByUserNameContaining(searchQuery);
     }
     
     public void delete(Long id) {
    	 userRepo.deleteById(id);
     }
}
