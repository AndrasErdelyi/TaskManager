package com.erdelyiandras.job.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
public class User{

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;
	private String name;
	private Date createdAt;
	private Date modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "RIGHTS_ID")
	private Rights rights;
	
	public Rights getRights() {
		return rights;
	}
	public void setRights(Rights rights) {
		this.rights = rights;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getModifiedAt() {
		return modifiedAt;
	}
	
	@PrePersist
	public void createdAt() {
		createdAt = new Date();
	}
	
	@PreUpdate
	public void modifiedAt() {
		modifiedAt = new Date();
	}
	
}