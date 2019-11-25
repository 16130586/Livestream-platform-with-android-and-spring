package com.t4.LiveServer.business.imp.mail;

import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.util.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailBusinessImp implements MailBusiness {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String sendMailForgot(String toEmail, String subject, int userId) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        String otp = Otp.generateOTP();
        helper.setTo(toEmail);
        helper.setSubject(subject);
        String text = "<h2>Your link to renew password: <a href=http://localhost:8080/renewPassword/" + userId + "/" + otp +">Click here!</a></h2>";
        helper.setText(text, true);

        javaMailSender.send(msg);
        return otp;
    }
}
