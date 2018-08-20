package com.myIGCoach.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {

	@Value( "${client.url}" )
    private  String clientUrl;

	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		httpServletResponse.addHeader("Access-Control-Allow-Origin", clientUrl);
		httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept,x-req");
		filterChain.doFilter(httpServletRequest, httpServletResponse);
		
		

	}
}