package com.example.demo.reposiroty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

/**
 * ユーザテーブルを操作.
 * 
 * @author isodakeisuke
 */
@Repository
public class UserRepository {

	/**
	 * ユーザーオブジェクトを作成するローマッパー
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザ情報を登録or更新
	 * 
	 * @param user
	 * @return 登録or更新したゆーざ情報（失敗した場合はnull）
	 */
	public User save(User user) throws Exception {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = null;

		try {
			if (user.getId() == null) /* ユーザ登録処理 */ {
				sql = "INSERT INTO users(name, email, password) VALUES (:name, :email, :password);";
				template.update(sql, param);

				// 登録したユーザidを入れ直して代入
				user.setId(getMaxUserId());
			} else /* ユーザ情報更新処理 */ {
				sql = "UPDATE users SET name = :name, email = :email, password = :password WHERE id = :id";
				template.update(sql, param);
			}
		} catch (Exception e) {
			user = null;
			e.printStackTrace();
			throw new Exception();
		}
		return user;
	}

	/**
	 * usersテーブル内の最大のidを取得
	 * 
	 * @return テーブル内の最大のid（データが無ければ0）
	 */
	private Integer getMaxUserId() {
		String sql = "SELECT * FROM users WHERE id = (SELECT MAX(id) FROM users)";
		List<User> userList = template.query(sql, USER_ROW_MAPPER);
		return userList.size() == 0 ? 0 : userList.get(0).getId();
	}

	/**
	 * メールアドレスからユーザーを検索します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報（該当するユーザーがいない場合はnull）
	 */
	public User findByEmail(String email) {
		String sql = "select id, name, email, password from users where email = :email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);

		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
}
