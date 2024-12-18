package com.backendLogin.backendLogin.securityconfig;

import com.backendLogin.backendLogin.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        // Aquí obtenemos el "sub" de Google que es un identificador único para el usuario
        String userId = oauth2User.getAttribute("sub"); // "sub" es el identificador único proporcionado por Google (en el caso de Google OAuth2)

        // Crear el JWT
        String jwtToken = jwtUtils.createToken(authentication, Long.valueOf(userId)); // Convertir el ID en Long (o adaptarlo a tu estructura)

        // Establecer el JWT en la cabecera de la respuesta
        response.setHeader("Authorization", "Bearer " + jwtToken);

        // Redirigir al usuario a la página principal (por ejemplo, dashboard)
        String targetUrl = UriComponentsBuilder.fromUriString("/dashboard")
                .build()
                .toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
