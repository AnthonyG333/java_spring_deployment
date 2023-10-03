package com.anthony.messagingapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anthony.messagingapp.models.Friend;
import com.anthony.messagingapp.models.PrivateMessage;
import com.anthony.messagingapp.models.User;
import com.anthony.messagingapp.services.PrivateMessageService;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PrivateMessageController {
	
	private final PrivateMessageService privateMessageServ;
	private final UserService userServ;
	
	public PrivateMessageController(PrivateMessageService privateMessageServ, UserService userServ) {
		this.privateMessageServ = privateMessageServ;
		this.userServ = userServ;
	}
	
	class sortItems implements Comparator<PrivateMessage>{
		@Override
		public int compare(PrivateMessage o1, PrivateMessage o2) {
			return o2.getCreatedAt().compareTo(o1.getCreatedAt());
		}
	}	
	
//	PRIVATE MESSAGES LANDING PAGE
	@GetMapping("/liaison/private/messages")
	public String privateMessages(@ModelAttribute("privateMessage") PrivateMessage privateMessage, Model model, HttpSession session) {
		
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		
		session.removeAttribute("message_id");
		
		return "PrivateMessage.jsp";
	}

//	PRIVATE MESSAGES DETAILS
	@GetMapping("/liaison/private/messages/get/{id}")
	public String privateMessagesGet(@ModelAttribute("privateMessage") PrivateMessage privateMessage, @PathVariable("id")Long id, Model model, HttpSession session) {
		
		User currUser = userServ.getById((Long)session.getAttribute("user_id"));
		User currFriend = userServ.getById(id);
		
		session.setAttribute("friend_id", id);
		List<PrivateMessage> allMessages = new ArrayList<PrivateMessage>();
		
//		BRUTE FORCE
		allMessages.addAll(currUser.findDMByRecipient(currFriend.getId()));
		allMessages.addAll(currFriend.findDMByRecipient(currUser.getId()));

//		EFFICIENCY
//		allMessages.addAll(privateMessageServ.getAllBetweenTwoUsers(currUser.getId(), currFriend.getId()));
//		allMessages.addAll(privateMessageServ.getAllBetweenTwoUsers(currFriend.getId(), currUser.getId()));

		Collections.sort(allMessages, new sortItems());
		model.addAttribute("currUser", currUser);
		model.addAttribute("currFriend", currFriend);
		model.addAttribute("allMessages", allMessages);
		
		return "PrivateMessageGet.jsp";
	}
	
	@PostMapping("/liaison/private/message/post")
	public String privateMessagePost(@Valid @ModelAttribute("privateMessage") PrivateMessage privateMessage, BindingResult result, Model model, HttpSession session){
		
		if(result.hasErrors()) {
			model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
			return "PrivateMessageGet.jsp";
		}
		
		privateMessageServ.create(privateMessage);
		
		return "redirect:/liaison/private/messages/get/" + (Long)session.getAttribute("friend_id");
	}

	@GetMapping("/liaison/friend/search")
	public String friendSearch(@RequestParam("searchQuery") String searchQuery, Model model, @ModelAttribute("friend")Friend friend, HttpSession session) {
		List<User> allUsers = userServ.searchByQuery(searchQuery);
		model.addAttribute("allUsers", allUsers);
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		model.addAttribute("searchQuery", searchQuery);
		return "FriendSearch.jsp";
	}
	
	@DeleteMapping("/liaison/private/message/delete/{id}")
	public String publicMessageDelete(@PathVariable("id") Long id, HttpSession session) {
		privateMessageServ.delete(id);
		return "redirect:/liaison/private/messages/get/" + (Long)session.getAttribute("friend_id");
	}

}
