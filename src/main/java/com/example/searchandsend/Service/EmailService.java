package com.example.searchandsend.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMessageWithAttachment(String to, String imagePath) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("serioussullyseal@gmail.com");
        helper.setTo(to);
        File imageFile = new File("src/main/resources/static/images/" + imagePath);
        if (!imageFile.exists()) {
            throw new FileNotFoundException("Image file not found: " + imagePath);
        }
        helper.addAttachment(imageFile.getName(), imageFile);
        helper.setSubject("Your Image");
        helper.setText("Please find the attached image.", false);

        mailSender.send(message);
    }
}
