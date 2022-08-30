package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.example.demo.domain.User;
import com.example.demo.form.SignupForm;
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
	 * ユーザの登録.
	 * 
	 * @param フォームに入力された情報
	 * @return insertしたユーザ情報（insertに失敗した場合はnullを返す）
	 * @throws Exception
	 */
	public User insert(SignupForm form) {

		// インスタンス化して受け取った値を代入
		User user = new User();
		BeanUtils.copyProperties(form, user);

		User userResult = null;
		// DBへのinsertを実行
		try {
			userResult = userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResult;
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
