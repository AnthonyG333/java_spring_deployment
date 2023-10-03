package com.anthony.messagingapp.models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Username is required.")
	@Size(min = 3, max = 25, message = "Username must be between 8 and 25 characters long.")
	private String userName;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Please enter a valid email.")
	private String email;
	
	@NotEmpty(message = "Password is required.")
	@Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters.")
	private String password;
	
	@NotEmpty(message = "Confirm password is required.")
	@Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters.")
	private String confirmPassword;
	
	@Size(min = 0, max = 225, message = "Bio must less than 255 characters.")
	private String bio = "";
	
	@Lob
	private Blob avi;
	
	@Lob
	private Blob header;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<PublicMessage> publicMessages;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<PrivateMessage> privateMessages;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Friend> friends;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Reply> replies;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	public User() {}
	
	public User(String userName, String email, String password, String confirmPassword) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public List<PrivateMessage> findDMByRecipient(Long recipientId){
		List<PrivateMessage> result = new ArrayList<PrivateMessage>();
		
		for(PrivateMessage message : this.getPrivateMessages()) {
			if(message.getRecipientId() == recipientId) {
				result.add(message);
			}
		}
		return result;
	}
	
	public void setPublicMessages(List<PublicMessage> publicMessages) {
		this.publicMessages = publicMessages;
	}

	public void setPrivateMessages(List<PrivateMessage> privateMessages) {
		this.privateMessages = privateMessages;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Blob getAvi() {
		return avi;
	}

	public void setAvi(Blob avi) {
		this.avi = avi;
	}

	public Blob getHeader() {
		return header;
	}

	public void setHeader(Blob header) {
		this.header = header;
	}

	public List<PublicMessage> getPublicMessages() {
		return publicMessages;
	}

	public List<PrivateMessage> getPrivateMessages() {
		return privateMessages;
	}

	public List<Friend> getFriends() {
		return friends;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
