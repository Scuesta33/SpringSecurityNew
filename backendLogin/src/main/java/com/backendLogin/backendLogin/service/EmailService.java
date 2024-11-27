package com.backendLogin.backendLogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
	private JavaMailSender mailSender;
    
    public void sendConfirmationEmail(String toEmail, String concertName, String concertDate, String concertLocation) throws MessagingException {
    	
    	//crea el mensaje de correo
    	MimeMessage message = mailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true);
    	
    	//configura el correo
    	helper.setTo(toEmail);
    	helper.setSubject("Confirmación de compra - Entrada para " + concertName);
    	helper.setText("<h1>¡Gracias por tu compra!</h1>" +
    	        "<p>Has comprado una entrada para el concierto: <strong>" + concertName + "</strong></p>" +
    	        "<p><strong>Fecha del concierto:</strong> " + concertDate + "</p>" +
    	        "<p><strong>Ubicación:</strong> " + concertLocation + "</p>" +
    	        "<p>¡Nos vemos en el concierto!</p>", true); // El segundo parámetro indica que es HTML

    	
    	//Envía el correo
    	mailSender.send(message);
    	
    	
    }
}
