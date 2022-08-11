package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.reposiroty.UserRepository;

/**
 * ユーザの業務処理を行う.
 * 
 * @author isodakeisuke
 *
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザの登録
	 */
	public void insert(User user) {
		userRepository.save(user);
	}

	/**
	 * ユーザをメールアドレスで検索
	 * 
	 * @param email
	 * @return ユーザ情報
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
