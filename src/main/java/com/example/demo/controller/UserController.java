//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.demo.domain.User;
//import com.example.demo.service.UserService;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//	@Autowired
//	private UserService userService;
//	
//	@RequestMapping("/insert")
//	public void insert() {
//		System.out.println("コントローラ");
//		User user = new User();
//		user.setName("TestUser1");
//		user.setEmail("TestEmail1");
//		user.setPassword("TextPassword");
//		
//		userService.insert(user);
//	}
//}
