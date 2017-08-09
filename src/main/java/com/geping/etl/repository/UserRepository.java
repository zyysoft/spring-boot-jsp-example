package com.geping.etl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geping.etl.entity.CommentNeo4j;
import com.geping.etl.entity.PostComment;
import com.geping.etl.entity.Posts;
import com.geping.etl.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

	@Query(value = "select u from users u where id =:userId")
	public Users queryUsers(@Param("userId") Integer userId);

	@Query("select u from posts u where user_id =:userId")
	public Posts queryPost(@Param("userId") Integer userId);
	
	/**
	 * 一对多的查询
	 * @param userId
	 * @return
	 */
	@Query("select u from postComment u where user_id =:userId")
	public List<PostComment> queryTest(@Param("userId") Integer userId);
	
	/**
	 * 执行本地SQL查询
	 * @param userId
	 * @return
	 */
	@Query(value="select count(1) " 
				 + " from bbs.t_posts_comment a,bbs.t_posts b " 
				 + " where a.posts_id=b.id"
				 + " and a.user_id=?1 "
				 + " group by a.user_id"
		,nativeQuery=true)
	public int queryUserCommentCnt(Integer userId);
	
	/**
	 * 通过选取多个类的字段，封装成单个POJO对象 
	 * @param userId
	 * @return
	 */
	@Query(value="select new com.geping.etl.entity.CommentNeo4j(a.userId,b.userId,count(1)) " 
			 + " from postComment a,posts b " 
			+ " where a.postsId=b.id"
			+ " and a.userId=?1 "
			+ " group by a.userId")
	public CommentNeo4j queryUserCommentCnt2(Integer userId);
	
	/**
	 * 通过选取多个类的字段，封装成单个POJO对象 
	 * @param userId
	 * @return
	 */
	@Query(value="select new com.geping.etl.entity.CommentNeo4j(a.userId,b.userId,count(1)) " 
			 + " from postComment a,posts b " 
			+ " where a.postsId=b.id"
			+ " group by a.userId")
	public List<CommentNeo4j> queryAllUserCommentCnt();
	
}