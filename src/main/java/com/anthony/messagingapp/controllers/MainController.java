package com.anthony.messagingapp.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.anthony.messagingapp.models.Friend;
import com.anthony.messagingapp.models.LoginUser;
import com.anthony.messagingapp.models.PublicMessage;
import com.anthony.messagingapp.models.User;
import com.anthony.messagingapp.services.PublicMessageService;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	
	private final UserService userServ;
	private final PublicMessageService publicMessageServ;
	
	
	public MainController(UserService userServ, PublicMessageService publicMessageServ) {
		this.userServ = userServ;
		this.publicMessageServ = publicMessageServ;
	}
	
//	LOGIN REGISTRATION
	@GetMapping("/liaison/login/registration")
	public String LoginRegistration(@ModelAttribute("user")  User user, @ModelAttribute("loginUser") LoginUser loginUser) {
		return "LoginRegistration.jsp"; 
	}
	
//	DASHBOARD
	@GetMapping("/liaison/home/{pageNumber}")
	public String home(Model model, HttpSession session, @ModelAttribute("publicMessage") PublicMessage publicMessage, @PathVariable("pageNumber") int pageNumber) {
		if(session.getAttribute("user_id") == null) {
			return "redirect:/liaison/login/registration";
		}
		
		Page<PublicMessage> publicMessages = publicMessageServ.publicMessagesPerPage(pageNumber - 1);
		int totalPages = publicMessages.getTotalPages();
		
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("publicMessages", publicMessages);
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		model.addAttribute("popularMessages", publicMessageServ.getPopularMessages());
		
		session.removeAttribute("message_id");
		session.removeAttribute("friend_id");
		
		return "Home.jsp";
	}
	
//	PROFILE PAGE 
	@GetMapping("/liaison/user/{id}")
	public String profilePage(@PathVariable("id") Long id, Model model, HttpSession session, @ModelAttribute("friend") Friend friend) {
		
		model.addAttribute("currUser", userServ.getById(id));

		session.removeAttribute("message_id");
		session.removeAttribute("friend_id");
		
		return "ProfilePage.jsp";
	}
	
	
//	PUBLIC MESSAGE SEARCH
	@GetMapping("/liaison/public/message/search")
	public String publicMessageSearch(@RequestParam("searchQuery") String searchQuery, Model model, HttpSession session) {
			
		List<PublicMessage> searchResults = publicMessageServ.searchByQuery(searchQuery);
		
		model.addAttribute("searchQuery", searchQuery);
		model.addAttribute("searchResults", searchResults);
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		model.addAttribute("popularMessages", publicMessageServ.getPopularMessages());
		
		return "SearchResults.jsp";
	}
	
}
