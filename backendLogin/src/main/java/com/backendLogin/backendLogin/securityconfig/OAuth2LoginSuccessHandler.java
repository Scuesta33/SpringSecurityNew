
package com.backendLogin.backendLogin.securityconfig;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.service.UserService;
import com.backendLogin.backendLogin.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Obtain user attributes (in this case, from Google)
        String email = oauth2User.getAttribute("email");
        String username = oauth2User.getAttribute("name");

        // Register or update the user in the database
        UserSec user = userService.registerOrUpdateOAuthUser("google", email, username);
        Long userId = user.getId();  // Extract the ID of the registered user

        // Create the JWT token with the user ID
        String jwtToken = jwtUtils.createToken(authentication, userId);  // Pass the ID (Long) here

        // Set the JWT in the response header
        response.setHeader("Authorization", "Bearer " + jwtToken);

        // Redirect the user to the desired page
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:4200/dashboard").build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
