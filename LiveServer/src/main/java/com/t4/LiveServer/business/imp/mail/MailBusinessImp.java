package com.t4.LiveServer.business.imp.mail;

import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    @Override
    public String sendMailForgot(String toEmail, String subject, int userId) throws MessagingException {
        User user = userRepository.findById(userId).get();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        String otp = Otp.generateOTP();
        helper.setTo(toEmail);
        helper.setSubject(subject);
        String text = "";
        text += "<h3 style=\"color:#222\">Hi "+user.getNickName()+",</h3><br>";
        text += "<h3 style=\"color:#222\">You're using reset password function:</h3>";
        text += "<h3 style=\"color:#222\"><a href=http://localhost:8080/renewPassword/" + userId + "/" + otp +">Click here to continue!</a></h3><br>";
        text += "<h3>Regards,</h3>";
        text += "<h3>4TTeam</h2>";
        helper.setText(text, true);

        javaMailSender.send(msg);
        return otp;
    }
}
