package com.nootch.services.Implementations;

import com.nootch.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailServiceImplementation implements EmailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendMail(/* MultipartFile[] file, */ String to, String subject, String body) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(senderEmail);
            mimeMessageHelper.setTo(to);
            // mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);

            /* left out because no file was causing errors
            for(var f: file)
                mimeMessageHelper.addAttachment(
                        f.getOriginalFilename(),
                        new ByteArrayResource(f.getBytes())
                );
             */

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            System.out.println("Error sending email to ".concat(to));
        }
    }
}
