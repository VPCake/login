package it.objectmethod.login.dao.impl;

import java.security.SecureRandom;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Service;

import it.objectmethod.login.bean.User;
import it.objectmethod.login.dao.IUserDao;

@Service
public class UserDaoImpl extends NamedParameterJdbcDaoSupport implements IUserDao {

	@Autowired
	public UserDaoImpl(DataSource datasource) {
		super();
		setDataSource(datasource);
	}

	@Override
	public User getUser(String mail, String password) {
		User user = null;
		String sql = "Select nome,cognome,email,password From logindb.user where email = ? and password = ?";
		BeanPropertyRowMapper<User> rm = new BeanPropertyRowMapper<User>(User.class);
		user = getJdbcTemplate().queryForObject(sql, new Object[] { mail, password }, rm);
		return user;
	}

	private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "0123456789";
	private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private static SecureRandom random = new SecureRandom();

	@Override
	public String generateRandomString(int length) {
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);

		}

		return sb.toString();

	}
}
