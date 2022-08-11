package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

/**
 * ユーザ情報をやり取りするAPI
 * 
 * @author isodakeisuke
 */
@RestController
@RequestMapping("/user")
public class UserApiController {
	
	
	@Autowired
	private UserService userService;
	
	/**
	 * ユーザの登録
	 * @param name 名前
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ステータス
	 */
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public Map<String, String> insert(String name, String email, String password) {
		User user = new User();
		System.out.println("名前" + name);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		System.out.println(user);
		userService.insert(user);
		Map<String, String> map = new HashMap<>();
		map.put("status_code", "200");
		return map;
	}
	
	
	
//	@RequestMapping(value="/test", method = RequestMethod.POST)
//	public Map<String, String> test(String name) {
//		Map<String, String> map = new HashMap<>();
//		System.out.println("名前" + name);
//		map.put("戻り値", "うまく行ったよ");
//		System.out.println("名前：" + user.getName());
//		return map;
//	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public Map<String, String> login(String email) {
		Map<String, String> map = new HashMap<>();
//		userService.findByEmail(email);
		System.out.println("リクエスト来た");
		return map;
	}
	
}
