package fr.fajitasmaster974.EvergreenData.Services;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import fr.fajitasmaster974.EvergreenData.Entities.Criteria;
import fr.fajitasmaster974.EvergreenData.Entities.Subject;
import fr.fajitasmaster974.EvergreenData.Entities.User;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {
    
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private ResourceLoader resourceLoader;

    public void SendMailTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("thomasburfinp@gmail.com");
        message.setSubject("tu dors ?");
        message.setText("zzzzzzzzzz");
        message.setFrom("i!");

        javaMailSender.send(message);
    }


    public void SendSubjectDocumentationsNotificationEmail(Subject subject, User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Subject " + subject.getTitle() + " need new documentations.");

            Resource resource = resourceLoader.getResource("classpath:templates/SubjectDocumentationEmail.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{subjectTitle}}", subject.getTitle());
            html = html.replace("{{subjectId}}", subject.getId().toString());
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch(Exception e) {
            System.out.println("error sending email to " + user.getEmail());
        }
    }


    public void SendUserAssignToSubjectEmail(Subject subject, User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Assign to " + subject.getTitle());

            Resource resource = resourceLoader.getResource("classpath:templates/UserAssignToSubjectEmail.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{subjectTitle}}", subject.getTitle());
            html = html.replace("{{subjectId}}", subject.getId().toString());

            helper.setText(html, true);
            javaMailSender.send(message);
        } catch(Exception e) {
            System.out.println("error sending email to " + user.getEmail());
        }
    }


    public void SendUserRemoveToSubjectEmail(Subject subject, User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Remove from " + subject.getTitle());

            Resource resource = resourceLoader.getResource("classpath:templates/UserRemoveToSubjectEmail.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{subjectTitle}}", subject.getTitle());
            html = html.replace("{{subjectId}}", subject.getId().toString());

            helper.setText(html, true);
            javaMailSender.send(message);
        } catch(Exception e) {
            System.out.println("error sending email to " + user.getEmail());
        }
    }

    public void SendCriteriaAssignToSubjectEmail(Subject subject, Criteria criteria, User user) {
        System.out.println(user.getEmail());
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("New criteria");

            Resource resource = resourceLoader.getResource("classpath:templates/CriteriaAssignToSubjectEmail.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{subjectTitle}}", subject.getTitle());
            html = html.replace("{{criteriaName}}", criteria.getName());
            html = html.replace("{{subjectId}}", subject.getId().toString());
            
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch(Exception e) {
            System.out.println("error sending email to " + user.getEmail());
        }
    }

    public void SendInvalidUserEmail(User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("get invalided bozo");

            Resource resource = resourceLoader.getResource("classpath:templates/InvalidUserEmail.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch(Exception e) {
            System.out.println("error sending email to " + user.getEmail());
        }
    }
}
