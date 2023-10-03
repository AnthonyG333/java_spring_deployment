package com.anthony.messagingapp.controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.anthony.messagingapp.models.PublicMessage;
import com.anthony.messagingapp.services.PublicMessageService;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PublicMessageController {
	
	private final PublicMessageService publicMessageServ;
	private final UserService userServ;
	
	public PublicMessageController(PublicMessageService publicMessageServ, UserService userServ) {
		this.publicMessageServ = publicMessageServ;
		this.userServ = userServ;
	}
	
	@PostMapping("/liaison/public/message/post/{pageNumber}")
	public String publicMessagePost(@Valid @ModelAttribute("publicMessage") PublicMessage publicMessage, BindingResult result, Model model, HttpSession session, @PathVariable("pageNumber")int pageNumber) {
		if(result.hasErrors()) {
			
			Page<PublicMessage> publicMessages = publicMessageServ.publicMessagesPerPage(pageNumber - 1);
			int totalPages = publicMessages.getTotalPages();
			
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("publicMessages", publicMessages);
			model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
			model.addAttribute("popularMessages", publicMessageServ.getPopularMessages());
			
			return "Home.jsp";
		}
		
		publicMessageServ.create(publicMessage);
		
		return "redirect:/liaison/home/1";
	}
	
	@DeleteMapping("/liaison/public/message/delete/{id}")
	public String publicMessageDelete(@PathVariable("id") Long id) {
		
		publicMessageServ.delete(id);
		return "redirect:/liaison/home/1";
	}
	

}
