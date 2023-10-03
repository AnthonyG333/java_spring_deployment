package com.anthony.messagingapp.controllers;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.anthony.messagingapp.models.LoginUser;
import com.anthony.messagingapp.models.User;
import com.anthony.messagingapp.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	private final UserService userServ;
	
	public UserController(UserService userServ) {
		this.userServ = userServ;
		
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleFileUploadError(Model model, HttpSession session) {
		model.addAttribute("fileError3", "File is too large!");
		model.addAttribute("user", new User());
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		return "UpdateProfile.jsp";
	}
	
	@PostMapping("/liaison/user/register")
	public String userCreatePost(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("password", "Match", "Passwords do not match.");
		}
		
		if(userServ.getByEmail(user.getEmail()) != null) {
			result.rejectValue("email", "Match", "Email already exists.");
		}
		
		if(result.hasErrors()) {
			model.addAttribute("loginUser", new LoginUser());
			return "LoginRegistration.jsp";
		}
		
		User newUser = userServ.createUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/liaison/home/1";
	}
	
	@PostMapping("/liaison/user/login")
	public String userLoginPost(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model, HttpSession session) {
		
		User currLoginUser = userServ.login(loginUser, result);
		if(result.hasErrors()) {
			model.addAttribute("user", new User());
			return "LoginRegistration.jsp";
		}
		
		session.setAttribute("user_id", currLoginUser.getId());
		return "redirect:/liaison/home/1";
	}
	
	@GetMapping("/liaison/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/liaison/login/registration";
	}
	
	@GetMapping("/liaison/user/update/get")
	public String getUpdateProfile(@ModelAttribute("user") User user, Model model, HttpSession session) {
		model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
		return "UpdateProfile.jsp";
	}
	
//	UPDATE METHOD
	
	@PutMapping("/liaison/user/update/put")
	public String userUpdate(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		
		Long id = (Long)session.getAttribute("user_id");
		User currUser = userServ.getById(id);
		
		if(!currUser.getEmail().equals(user.getEmail())) {
			if(userServ.getByEmail(user.getEmail()) != null) {
				result.rejectValue("email", "Match", "Email already exists.");
			}
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			result.rejectValue("password", "Match", "Passwords do not match.");
		}
		
		if(result.hasErrors()) {
			return "UpdateProfile.jsp";
		}
		
		user.setAvi(currUser.getAvi());
		user.setHeader(currUser.getHeader());
		
		userServ.createUser(user);
		return "redirect:/liaison/user/" + id;
	}
	
//	AVI & HEADERS POST/GET METHODS 
	
	@PostMapping("/liaison/user/avi/post")
	public String aviPost(@RequestParam("image") MultipartFile file, HttpSession session, Model model) throws IOException, SerialException, SQLException {
		
		if(file.getSize() == 0) {
			model.addAttribute("fileError1", "File cannot be empty!");
			model.addAttribute("user", new User());
			model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
			return "UpdateProfile.jsp";
			
		}
		
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		
		Long id = (Long)session.getAttribute("user_id");
		User currUser = userServ.getById(id);
		currUser.setAvi(blob);
		userServ.saveImage(currUser);
		
		return "redirect:/liaison/user/" + id;
	}
	
	
	@GetMapping("/liaison/user/avi/view/{id}")
	public void aviShowOne(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		User currUser = userServ.getById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(currUser.getAvi().getBytes(1, (int)currUser.getAvi().length()));
	    response.getOutputStream().close();
	    
	}
	
	@PostMapping("/liaison/user/header/post")
	public String headerPost(@RequestParam("image") MultipartFile file, HttpSession session, Model model) throws IOException, SerialException, SQLException {
		
		if(file.getSize() == 0) {
			model.addAttribute("fileError2", "File cannot be empty!");
			model.addAttribute("user", new User());
			model.addAttribute("currUser", userServ.getById((Long)session.getAttribute("user_id")));
			return "UpdateProfile.jsp";
			
		}
		
		byte[] bytes = file.getBytes();
		Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		
		Long id = (Long)session.getAttribute("user_id");
		User currUser = userServ.getById(id);
		currUser.setHeader(blob);
		userServ.saveImage(currUser);
		
		return "redirect:/liaison/user/" + id;
	}
	
	@GetMapping("/liaison/user/header/view/{id}")
	public void headerShowOne(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		User currUser = userServ.getById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(currUser.getHeader().getBytes(1, (int)currUser.getHeader().length()));
	    response.getOutputStream().close();
	}
	
	@DeleteMapping("/liaison/user/delete")
	public String userDelete(HttpSession session) {
		userServ.delete((Long)session.getAttribute("user_id"));
		return "redirect:/liaison/login/registration";
	}
}
