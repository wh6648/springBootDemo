package com.rijia.workPlatform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "t_user")
public class UserEntity {

	@Id
	@Length(max = 36)
	@Column(length = 36, nullable = false)
	@GeneratedValue(generator = "guid")
	@GenericGenerator(name = "guid", strategy = "guid")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	public UserEntity() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}