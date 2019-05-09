package com.ipv.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ipv.exception.TokenValidateFailedException;
import com.ipv.service.JWTService;
import com.ipv.util.Constant;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

/**
 * 
 * The filter for validating / reassigning the jwt token
 * 
 * reference https://www.baeldung.com/spring-boot-add-filter
 * 
 * */
@Component
@Order(1)
public class JWTFilter implements Filter{
	
	@Autowired
    private JWTService jwtService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		JWTUserInfoWrapper jwtInfo = null;
		
		// validate the token 
		System.out.println("====="+req.getRequestURI());
		if (req.getRequestURI().contains("api") 
				&& !req.getRequestURI().contains("validate") 
				&& !req.getRequestURI().contains("create_user")
				&& !req.getRequestURI().contains("swagger-ui")) {
			String token = req.getHeader(Constant.JWT_TOKEN_HEADER);
			System.out.println("token:"+token);
			if (token == null || (jwtInfo = jwtService.validate(token)) == null) {
				res.sendError(HttpStatus.FORBIDDEN.value(), "Token is invalid or expired");
				return;
			}
			req.getSession().setAttribute("token", jwtInfo);
			res.setHeader(Constant.JWT_TOKEN_HEADER, jwtInfo.getNewJWT());
		}
		
		// continue the process
		chain.doFilter(request, response);
		
	}

}
