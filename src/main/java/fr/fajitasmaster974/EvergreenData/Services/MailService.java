package fr.fajitasmaster974.EvergreenData.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    
    @Autowired private JavaMailSender javaMailSender;

    public void SendMailTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("thomasburfinp@gmail.com");
        message.setSubject("tu dors ?");
        message.setText("zzzzzzzzzz");
        message.setFrom("i!");

        javaMailSender.send(message);
    }
}
