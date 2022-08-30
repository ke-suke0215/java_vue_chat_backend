package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.form.SignupForm;
import com.example.demo.service.UserService;

/**
 * ユーザ情報をやり取りするAPI
 * 
 * @author isodakeisuke
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserApiController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public SignupForm setUpForm() {
		return new SignupForm();
	}

	/**
	 * ユーザの登録
	 * 
	 * @param 入力されたユーザ情報
	 * @param 入力値チェックの情報
	 * @return ユーザ情報（正常に登録できた場合）、エラー有無の真偽値、エラーメッセージのmap が入ったmap
	 */
	@RequestMapping(value = "/insert", produces = { "application/json" }, method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody @Validated SignupForm form, BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> errorMessageMap = new HashMap<String, Object>();

		// 追加の入力値チェック
		result = enteredValueValidation(form, result);

		if (result.hasErrors()) /* エラーがあったとき */ {
			// エラーメッセージをmapに格納
			for (FieldError error : result.getFieldErrors()) {
				String fieldName = error.getField();
				if (!errorMessageMap.containsKey(fieldName)) {
					errorMessageMap.put(fieldName, error.getDefaultMessage());
				}
			}
		} else {
			User user = userService.insert(form);
			if(user == null) /* insertに失敗した場合 */{
				errorMessageMap.put("insertError", "登録に失敗しました");
			} else {
				map.put("user", user);
			}
		}
		
		map.put("hasError", errorMessageMap.size() != 0);
		map.put("errorMessages", errorMessageMap);
		return map;
	}

	/**
	 * フォームクラスで対応できていない入力値チェックを行う.
	 * 
	 * @param form   フォームに入力された値
	 * @param result エラーを格納するオブジェクト
	 * @return 追加の入力値チェックを行った後のエラーを格納するオブジェクト
	 */
	private BindingResult enteredValueValidation(@Validated SignupForm form, BindingResult result) {
		String password = form.getPassword();
		String confirmPassword = form.getConfirmPassword();

		// フォームクラスで複数のエラーを指定すると条件がかぶったとき順番がランダムで帰ってくるため、パスワードの文字数制限は直書きする
		boolean isInValidPasswordLength = password.length() < 8 || password.length() > 16;
		if (!password.equals("") && isInValidPasswordLength) /* 空欄では無いが適切でない長さのとき */ {
			result.rejectValue("password", null, "パスワードは8文字以上16文字以内で入力してください");
		}

		// 確認用パスワードが一致しているか判定（確認用パスワードが空欄ではないときのみ）
		if (!confirmPassword.equals("") && !password.equals(form.getConfirmPassword())) {
			result.rejectValue("confirmPassword", null, "パスワードが一致していません");
		}

		return result;
	}

	/**
	 * ログイン
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Map<String, String> login(String email) {
		Map<String, String> map = new HashMap<>();
		return map;
	}

}
