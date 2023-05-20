package com.quest.etna.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "address")
	public class Address {
		
		@Id
		@GeneratedValue (strategy = GenerationType.AUTO)
		@Column(nullable = false, length = 11)
		private int id;
		
		
		@Column(name = "street", nullable = false, length = 100)
		private String street;
		
		@Column(name = "postalCode", nullable = false, length = 30)
		private String postalCode;
		
		@Column(name = "city", nullable = false, length = 50)
		private String city;
		
		@Column(name = "country", nullable = false, length = 50)
		private String country;
		
		
		@ManyToOne
		@JoinColumn(name = "user_id")
		//@Column(nullable = false)
		private User user;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = true)
		private Date creationDate;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = true)
		private Date updatedDate;
		
		public Address() {}

		public Address(String street, String postalCode, String city, String country) {
		
			this.street = street;
			this.postalCode = postalCode;
			this.city = city;
			this.country = country;
			
		}
		
		@PrePersist
	    public void prePersist() {
	        this.creationDate = new Date();
	        this.updatedDate = new Date();
	    }

	    @PreUpdate
	    public void preUpdate() {
	        this.updatedDate = new Date();
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
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
		public String toString() {
			return "Address [id=" + id + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city
					+ ", country=" + country + ", user=" + user + ", creationDate=" + creationDate + ", updatedDate="
					+ updatedDate + "]";
		}
		
		
		
}

