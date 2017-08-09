package com.geping.etl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geping.etl.entity.Users;
import com.geping.etl.entity.res.ResultObject;
import com.geping.etl.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ResultObject<Users> getUser(@PathVariable("userId") Integer userId){
		ResultObject<Users> resultObject = new ResultObject<>();
		Users user = userService.getUser(userId);
		resultObject.setDomain(user);
		return resultObject;
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ResultObject<Users> test(){
		ResultObject<Users> resultObject = new ResultObject<>();
		return resultObject;
	}

}
