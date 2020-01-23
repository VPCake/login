package it.objectmethod.login.dao;

import it.objectmethod.login.bean.User;

public interface IUserDao {

	User getUser(String mail, String password);

	String generateRandomString(int length);

}
