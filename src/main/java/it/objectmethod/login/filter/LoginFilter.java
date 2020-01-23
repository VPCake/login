package it.objectmethod.login.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.login.auth.AuthenticateTable;

@Component
public class LoginFilter implements Filter {

	@Autowired
	private AuthenticateTable auth;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String token = httpRequest.getHeader("token");

		if (httpRequest.getRequestURI().endsWith("/login")) {
			chain.doFilter(httpRequest, httpResponse);
		} else {
			if (token != null && auth.getAuthTable().containsKey(token)) {
				chain.doFilter(httpRequest, httpResponse);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

}
