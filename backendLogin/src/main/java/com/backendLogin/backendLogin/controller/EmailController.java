package com.backendLogin.backendLogin.controller;

import com.backendLogin.backendLogin.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Endpoint para enviar correo de confirmación
    @PostMapping("/sendConfirmation")
    public String sendConfirmationEmail(
            @RequestParam String toEmail,
            @RequestParam String concertName,
            @RequestParam String concertDate,
            @RequestParam String concertLocation) {
        
        try {
            // Llama al servicio para enviar el correo
            emailService.sendConfirmationEmail(toEmail, concertName, concertDate, concertLocation);
            return "Correo de confirmación enviado exitosamente a " + toEmail;
        } catch (MessagingException e) {
            // Si ocurre un error al enviar el correo, retorna un mensaje de error
            return "Error al enviar el correo de confirmación: " + e.getMessage();
        }
    }
}
