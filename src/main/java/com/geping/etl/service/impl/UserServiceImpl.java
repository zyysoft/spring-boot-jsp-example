package com.geping.etl.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geping.etl.entity.Users;
import com.geping.etl.repository.UserRepository;
import com.geping.etl.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Users getUser(Integer userId) {
		logger.info(userRepository.queryAllUserCommentCnt().toString());
		return userRepository.findOne(userId);
	}
}
