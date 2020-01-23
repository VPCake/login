package it.objectmethod.login.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.login.auth.AuthenticateTable;
import it.objectmethod.login.bean.User;
import it.objectmethod.login.dao.IUserDao;

@RestController
public class LoginController {

	@Autowired
	public IUserDao userDao;

	@Autowired
	private AuthenticateTable auth;

	@PostMapping("/login")
	public ResponseEntity<User> getUser(@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "password", required = true) String password) {
		User user = new User();
		ResponseEntity<User> msg = null;
		user = userDao.getUser(email, password);

		if (user.getMail() != null) {
			String token = userDao.generateRandomString(12);

			HashMap<String, User> mappa = new HashMap<String, User>();
			mappa.put(token, user);
			msg = new ResponseEntity<>(HttpStatus.ACCEPTED);
		} else {
			msg = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		return msg;

	}

	@GetMapping("/area-riservata")
	public String areaRiservata(@RequestHeader(name = "token") String token) {

		User user = auth.getAuthTable().get(token);
		return "Benvenuto, " + user.getNome();
	}

}
