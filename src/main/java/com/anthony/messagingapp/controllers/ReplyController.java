package com.anthony.messagingapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.anthony.messagingapp.models.Friend;
import com.anthony.messagingapp.models.Reply;
import com.anthony.messagingapp.services.PublicMessageService;
import com.anthony.messagingapp.services.ReplyService;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ReplyController {
	
	private final ReplyService replyServ;
	private final PublicMessageService publicMessageServ;
	private final UserService userServ;
	
	public ReplyController(ReplyService replyServ, PublicMessageService publicMessageServ, UserService userServ) {
		this.replyServ = replyServ;
		this.publicMessageServ = publicMessageServ;
		this.userServ = userServ;
	}

//	PROFILE PAGE REPLIES
	@GetMapping("/liaison/user/replies/{id}")
	public String profilePageReplies(@PathVariable("id") Long id, Model model, HttpSession session, @ModelAttribute("friend") Friend friend) {
		
		model.addAttribute("currUser", userServ.getById(id));

		session.removeAttribute("friend_id");
		
		return "ProfilePage2.jsp";
	}
	
//	PARENT MESSAGE PAGE
	@GetMapping("/liaison/reply/get/{id}")
	public String replyGet(@PathVariable("id") Long id, @ModelAttribute("reply") Reply reply, Model model, HttpSession session) {
		
		session.setAttribute("message_id", publicMessageServ.getOne(id).getId());
		
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		model.addAttribute("currMessage", publicMessageServ.getOne((Long)session.getAttribute("message_id")));
		model.addAttribute("popularMessages", publicMessageServ.getPopularMessages());
		
		return "Reply.jsp";
	}
	
	
//	POST REPLY
	@PostMapping("/liaison/reply/post")
	public String replyPost(@Valid @ModelAttribute("reply") Reply reply, BindingResult result, Model model, HttpSession session) {
		if(result.hasErrors()) {
			model.addAttribute("currMessage", publicMessageServ.getOne((Long)session.getAttribute("message_id")));
			return "Reply.jsp";
		}
		
		replyServ.create(reply);
				
		publicMessageServ.incrementPopularity((Long)session.getAttribute("message_id"));
		
		return "redirect:/liaison/reply/get/" + (Long)session.getAttribute("message_id");
	}
	
//	DELETE REPLY FROM PARENT MESSAGE
	@DeleteMapping("/liaison/reply/delete/{id}")
	public String replyDelete(@PathVariable("id")Long id, HttpSession session) {
		
		publicMessageServ.decrementPopularity((Long)session.getAttribute("message_id"));
		
		replyServ.delete(id);
		
		return "redirect:/liaison/reply/get/" + (Long)session.getAttribute("message_id");
		
	}
	
}
