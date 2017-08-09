package com.geping.etl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 
 * @author geping 用户评论的关系
 */
@Entity(name = "postComment")
@Table(name = "t_posts_comment", catalog = "bbs")
public class PostComment {
	
	@Id
	private Integer id;
	// 被邀请人/被评论人
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "posts_id")
	private Integer postsId;
	
	/**
	 * 一对多，jpa会根据posts.id=?查询数据
	 */
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="posts_id",referencedColumnName="id",insertable = false, updatable = false)
	private Posts posts;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPostsId() {
		return postsId;
	}

	public void setPostsId(Integer postsId) {
		this.postsId = postsId;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return String.format("PostComment[id=%d, userId=%s,postsId=%d", id, userId,postsId)+" post:"+posts.toString()+"]";
	}

}
