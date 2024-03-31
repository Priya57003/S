package com.springsecurity.demo.filter;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.demo.service.JwtService;
import com.springsecurity.demo.service.UserDetailsImp;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JwtService jwtservice;
	private final UserDetailsImp userdetailsService;
	
	public JwtAuthenticationFilter(JwtService jwtservice, UserDetailsImp userdetailsService) {
		this.jwtservice = jwtservice;
		this.userdetailsService = userdetailsService;
	}

	@Override
	protected void doFilterInternal(
			@Nonnull HttpServletRequest request,
			@NonNull HttpServletResponse response, 
			@Nonnull FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authHeader=request.getHeader("Authorization");
		if(authHeader==null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return ;
		}
		String token=authHeader.substring(7);
		String username=jwtservice.extractUsername(token);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails=userdetailsService.loadUserByUsername(username);
			if(jwtservice.isValid(token, userdetails)) {
				UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(
						userdetails,null,userdetails.getAuthorities()
				);
				authtoken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
