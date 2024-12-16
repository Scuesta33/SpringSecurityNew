package com.backendLogin.backendLogin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendConfirmationEmail(String toEmail, String concertName, String concertDate, String concertLocation) throws MessagingException {
        
        // Crea el mensaje de correo
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            // Configura el correo
            helper.setFrom("scuesta33@gmail.com");  // Si usas Gmail, también podrías usar la dirección configurada en properties
            helper.setTo(toEmail);
            helper.setSubject("Confirmación de compra - Entrada para " + concertName);
            helper.setText("<h1>¡Gracias por tu compra!</h1>" +
                    "<p>Has comprado una entrada para el concierto: <strong>" + concertName + "</strong></p>" +
                    "<p><strong>Fecha del concierto:</strong> " + concertDate + "</p>" +
                    "<p><strong>Ubicación:</strong> " + concertLocation + "</p>" +
                    "<p>¡Nos vemos en el concierto!</p>", true); // El segundo parámetro indica que es HTML

            // Envía el correo
            mailSender.send(message);
            logger.info("Correo enviado a: " + toEmail);

        } catch (MessagingException e) {
            // Si hay un error al enviar el correo, lo capturamos y lo logueamos
            logger.error("Error al enviar el correo a " + toEmail, e);
            throw new MessagingException("Error al enviar el correo de confirmación", e); // Puedes lanzar la excepción de nuevo si lo deseas
        }
    }
    
}
