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
import org.springframework.stereotype.Component;

import com.ipv.exception.TokenValidateFailedException;
import com.ipv.service.JWTService;
import com.ipv.util.Constant;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

//reference https://www.baeldung.com/spring-boot-add-filter


/**
 * 
 * The filter for validating / reassigning the jwt token
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
		
		//validate the token
		if (!req.getRequestURI().contains("validate")) {
			String token = req.getHeader(Constant.JWT_TOKEN_HEADER);
			if (token == null || (jwtInfo = jwtService.validate(token)) == null) {
				throw new TokenValidateFailedException("Token is invalid or expired");
			}
			req.getSession().setAttribute("token", jwtInfo);
		}
		chain.doFilter(request, response);
		//renew the token
		res.setHeader(Constant.JWT_TOKEN_HEADER, jwtInfo.getNewJWT());
	}

}
