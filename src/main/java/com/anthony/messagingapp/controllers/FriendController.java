package com.anthony.messagingapp.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.anthony.messagingapp.models.Friend;
import com.anthony.messagingapp.models.User;
import com.anthony.messagingapp.services.FriendService;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FriendController {
	
	private final FriendService friendServ;
	private final UserService userServ;
	
	public FriendController(FriendService friendServ, UserService userServ) {
		this.friendServ = friendServ;
		this.userServ = userServ;
	}
	
	@PostMapping("/liaison/friend/post")
	public String friendPost(@ModelAttribute("friend") Friend friend, HttpSession session, Model model) {
//		The following code works to validate the "add friend" request. If the username already exists in the currently logged in users friendlist, redirect with error.
//		Retrieving the currently logged in user.
		User currUser = userServ.getById((Long)session.getAttribute("user_id"));
		

//		Retrieving the friend list from the currently logged in user.
		List<Friend> allFriends = currUser.getFriends();
//		Retrieving the username of the requested friend
		String userName = friend.getUserName();
//		Looping through the currently logged in users friend list
		for(Friend testFriend : allFriends) {
//			if the username of the requested friend equals an element within the currently logged in users friend list,
//				return errors and do not create friend
			if(testFriend.getUserName().equals(userName)) {
				String friendError = "User already exists in your friends list!";
				model.addAttribute("currUser", userServ.getById(currUser.getId()));
				model.addAttribute("friendError", friendError);
				return "ProfilePage.jsp";
			}
		}
//		if we make it out of the for loop without encountering the requested  username, create the friend and add to the users friends list
		friendServ.create(friend);
		
	 	return "redirect:/liaison/private/messages";
	}
	
	
	@DeleteMapping("/liaison/friend/delete/{id}")
	public String friendDelete(@PathVariable("id") Long id, HttpSession session) {
		
		friendServ.delete(id);
		return "redirect:/liaison/user/" + (Long)session.getAttribute("user_id");
	}

}
