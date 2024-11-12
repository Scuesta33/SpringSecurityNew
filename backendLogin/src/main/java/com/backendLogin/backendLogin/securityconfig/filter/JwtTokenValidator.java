package com.backendLogin.backendLogin.securityconfig.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backendLogin.backendLogin.utils.JwtUtils;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{
 
	private JwtUtils jwtUtils;
	
    public JwtTokenValidator(JwtUtils jwtUtils) {
    	this.jwtUtils = jwtUtils;
    }
    
   

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException
		  {
		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (jwtToken!=null) {
			//bearer
			jwtToken = jwtToken.substring(7);
			DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
			
			String username = jwtUtils.extractUsername(decodedJWT);
			String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
			
			Collection <? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
					
		}
		
		filterChain.doFilter(request, response);
		
	}
}