package com.geping.etl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "posts")
@Table(name = "t_posts", catalog = "bbs")
public class Posts {

	@Id
	private Integer id;
	
	@Column(name = "user_id")
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("Posts[id=%d, userId=%s]", id, userId);
	}

}
