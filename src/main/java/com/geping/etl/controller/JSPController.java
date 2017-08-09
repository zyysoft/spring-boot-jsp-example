package com.geping.etl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.geping.etl.entity.Users;
import com.geping.etl.entity.res.ResultObject;
import com.geping.etl.service.UserService;

@Controller
@RequestMapping(value="/jsp")
public class JSPController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ModelAndView getUser(@PathVariable("userId") Integer userId){
		ModelAndView model = new ModelAndView();
		ResultObject<Users> resultObject = new ResultObject<>();
		Users user = userService.getUser(userId);
		resultObject.setDomain(user);
		model.addObject("resultObject", user);
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public ResultObject<Users> test(){
		ResultObject<Users> resultObject = new ResultObject<>();
		return resultObject;
	}

}
