package com.quest.etna.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;



@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(nullable = false, length = 11)
	private int id;
	
	
	@Column(nullable = false, length = 255, unique = true)
	private String username;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = true, length = 255)
	private UserRole role = UserRole.ROLE_USER;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date creationDate = new Date();
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date updatedDate = new Date();

	public User() {}

	public User(int id, String username, String password, UserRole role, Date creationDate, Date updatedDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.creationDate = creationDate;
		this.updatedDate = updatedDate;
	}

	
	public User(String username, String password) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationDate, id, password, role, updatedDate, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(creationDate, other.creationDate) && id == other.id
				&& Objects.equals(password, other.password) && role == other.role
				&& Objects.equals(updatedDate, other.updatedDate) && Objects.equals(username, other.username);
	}
	
}
