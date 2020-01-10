package com.t4.LiveServer.business.imp.mail;

import com.t4.LiveServer.business.interfaze.mail.MailBusiness;
import com.t4.LiveServer.config.ReportConfig;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.repository.StreamRepository;
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

    @Autowired
    StreamRepository streamRepository;

    @Override
    public String sendMailForgot(String toEmail, String subject, int userId) throws MessagingException {
        User user = userRepository.findById(userId).get();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        String otp = Otp.generateOTP();
        helper.setTo(toEmail);
        helper.setSubject(subject);
        String text = "";
        text += "<div style=\"margin:0;padding:0;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;font-weight:300;min-height:100%;height:100%;width:100%\">\n" +
                "<table id=\"m_4904713513055239605wrapper\" cellpadding=\"20\" cellspacing=\"0\" border=\"0\" style=\"width:100%;background-color:#eaeaea;background-image:url(https://ci6.googleusercontent.com/proxy/ql83XJ9oLXv_1MgTWkzlBGmVr-Jdc4KxOzmycb4GNbj1ie38oOvcENkZjZS1Di59J4wTcnWP8-fLBNtdnHVYuSnmQGPOpacnZoN7m1qVYarR72U=s0-d-e1-ft#https://static0.twilio.com/resources/images/email/background.jpg);font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;font-weight:300;border-collapse:collapse;margin:0;padding:0;line-height:100%;height:100%\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t<table id=\"m_4904713513055239605contentTable\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#fff;margin:0 auto;width:680px;border:solid 1px #ddd;border-collapse:collapse\">\n" +
                "\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t<table id=\"m_4904713513055239605header\" cellpadding=\"20\" cellspacing=\"0\" border=\"0\" style=\"border-bottom:solid 1px #ddd;width:100%;border-collapse:collapse\">\n" +
                "\t\t\t\t\t<tbody><tr><td style=\"color:#444;font-size:31px;font-weight:bold;border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t\t\t\t\t<h4 style=\"color: red; margin: .5rem;\">4T Team</h4>\n" +
                "\t\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t\t<table cellpadding=\"30\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t\t\t\t\t<tbody><tr><td id=\"m_4904713513055239605message\" style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t\t\t<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t\t<span class=\"notranslate\">Hi "+ user.getNickName() +"</span>,\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t\tYou're using forgot password function.<br>\n" +
                "\t\t\tTo next step, please click link below:\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t\t<a href=\"http://localhost:8080/user/renewPassword/" + userId + "/" + otp +"\" class=\"notranslate\" style=\"color:#0000ff;font-weight:400;text-decoration:underline\" target=\"_blank\">Reset your password</a>\n" +
                "\t\t</p>\n" +
                "    </td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t    <br>\n" +
                "\t        Or, copy and paste the following URL into your browser: <br>\n" +
                "\t\t    <span class=\"notranslate\"><a href=\"http://localhost:8080/user/renewPassword/" + userId + "/" + otp +"\" target=\"_blank\" >http://localhost:8080/user/renewPassword/" + userId + "/" + otp +"</a></span>\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t</td></tr></tbody>\n" +
                "\t\t</table>\n" +
                "\n" +
                "\t\t<table id=\"m_4904713513055239605contentTable\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin:0 auto;width:680px;border-collapse:collapse\">\n" +
                "\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;text-align:center;border-collapse:collapse\">\n" +
                "\t\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t\t<br>\n" +
                "\n" +
                "\t\t\t\t\t<p style=\"font-size:12px;color:#555;line-height:19px;font-weight:300;margin:0 30px;text-align:center\">\n" +
                "\t\t\t\t\t\tThis system email was sent to <span class=\"notranslate\">"+user.getNickName()+"</span> (<span class=\"notranslate\"><a href=\""+user.getGmail()+"\" style=\"color:#555;font-weight:300;text-decoration:none\" target=\"_blank\">"+user.getGmail()+"</a></span>) <span></span>regarding your 4T Account\n" +
                "\t\t\t\t\t\t<br>by 4T, DH16DT, NLU, Ho Chi Minh city\n" +
                "\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t<br>\n" +
                "\n" +
                "\t\t\t\t\t<p style=\"font-size:14px;color:#555;line-height:16px;font-weight:300;margin:20px 30px;text-align:center\">\n" +
                "\t\t\t\t\t\tIf you have any questions, simply respond to this email and we'll be happy to help.\n" +
                "\t\t\t\t\t\t</p>\n" +
                "\n" +
                "\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t</td></tr></tbody>\n" +
                "\t\t</table>\n" +
                "\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<img src=\"https://ci5.googleusercontent.com/proxy/lx35MxpF8xhHCRmzU_XiaMD5i4nW3bUKi0yQHVPR2P2VnNpsR4zfLzswf5LomJV0sNkq4FCWiny-qJD6n_k24neFrjEpCyrfcf92TbW3mOfwhPtrVZRrxrmvVncBOKcJe1JcHqhN5nkatJ_g6pvpbjcbxE1fpepQULasYO1x2x1sOMo0GRMMcq39C_9xzC8Avcy0-1UY8uxtOV2gDzwQMqsMFlbfwLvc4Cv8xypaB5BpynHJgAnvAxuljOgYfAleAn7ntGuVH-NwUObLPkrYZsBQaS2XimjzfV9L39L8TSdtOiVB9m_wuLCMDdJxLtaaQwOf6cYdrs-r_ht2W93CNABOilUhg1vYFdIKpzpC5y77rnYccvRMV8E_5mB5_7lo1YV1Vwpwi3roycn2EtHAYpccvNmBVk5TWNZ3FfolpmgFFxvFmZb2kONZZh5v9SVdAPAMO2y8jiOlGN7SQSDJSYzRExw5Bw=s0-d-e1-ft#https://links.twiliocdn.com/wf/open?upn=C-2BR1nQLwx9EnFchUAUYdvcD1iPRWM3940mvfArlUqx6mnMGLQJ5YmI3h8AO-2FWgo4msdm6eD5sZEXSaSbM5gpOUZr90xESTmml6bIfNI7WeAuCBkKSoGpzzaoSW5YSX5-2FLX9ckbh9LbgIominaHH0nMIoQjSYbmwtVGLa0d-2BAyOAb5FCKuf49L-2FtL7a0poJ0dPgCIuVgLBiD7fLb2hJLvKw1BKgEYu2lsO2-2Bva6hS31HpyClbryTXc9-2FS9Z98ugc3qFqEwDxQVMvUpYvjtmNy0g-3D-3D\" alt=\"\" width=\"1\" height=\"1\" border=\"0\" style=\"height: 1px !important; width: 1px !important; border-width: 0px !important; margin: 0px !important; padding: 0px !important; display: none !important;\" class=\"CToWUd\" hidden=\"\"><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "</div></div>";
        helper.setText(text, true);

        javaMailSender.send(msg);
        return otp;
    }

    @Override
    public void sendMailInformReport(String subject, int streamId) throws MessagingException {
        Stream stream = streamRepository.findById(streamId).get();
        User user = stream.getOwner();
        String email = user.getGmail();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setTo(new String[]{email, ReportConfig.ADMIN_MAIL});
        helper.setSubject(subject);
        String text = "";
        text += "<div style=\"margin:0;padding:0;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;font-weight:300;min-height:100%;height:100%;width:100%\">\n" +
                "<table id=\"m_4904713513055239605wrapper\" cellpadding=\"20\" cellspacing=\"0\" border=\"0\" style=\"width:100%;background-color:#eaeaea;background-image:url(https://ci6.googleusercontent.com/proxy/ql83XJ9oLXv_1MgTWkzlBGmVr-Jdc4KxOzmycb4GNbj1ie38oOvcENkZjZS1Di59J4wTcnWP8-fLBNtdnHVYuSnmQGPOpacnZoN7m1qVYarR72U=s0-d-e1-ft#https://static0.twilio.com/resources/images/email/background.jpg);font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;font-weight:300;border-collapse:collapse;margin:0;padding:0;line-height:100%;height:100%\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t<table id=\"m_4904713513055239605contentTable\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#fff;margin:0 auto;width:680px;border:solid 1px #ddd;border-collapse:collapse\">\n" +
                "\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t<table id=\"m_4904713513055239605header\" cellpadding=\"20\" cellspacing=\"0\" border=\"0\" style=\"border-bottom:solid 1px #ddd;width:100%;border-collapse:collapse\">\n" +
                "\t\t\t\t\t<tbody><tr><td style=\"color:#444;font-size:31px;font-weight:bold;border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t\t\t\t\t<h4 style=\"color: red; margin: .5rem;\">4T Team</h4>\n" +
                "\t\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t\t<table cellpadding=\"30\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t\t\t\t\t<tbody><tr><td id=\"m_4904713513055239605message\" style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t\t\t<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t\t<span class=\"notranslate\">Hi "+ user.getNickName() +"</span>,\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t\tStream \""+stream.getTitle()+"\" is reported.<br>\n" +
                "\t\t\t\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t</p>\n" +
                "    </td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;border-collapse:collapse\">\n" +
                "\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\t\t<p id=\"m_4904713513055239605description\" style=\"font-size:16px;color:#555;line-height:26px;font-weight:300;margin:0 40px\">\n" +
                "\t\t    <br>\n" +
                "\t\t</p>\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t</td></tr></tbody>\n" +
                "\t\t</table>\n" +
                "\n" +
                "\t\t<table id=\"m_4904713513055239605contentTable\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin:0 auto;width:680px;border-collapse:collapse\">\n" +
                "\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t<table cellpadding=\"10\" cellspacing=\"0\" border=\"0\" style=\"width:100%;text-align:center;border-collapse:collapse\">\n" +
                "\t\t\t\t<tbody><tr><td style=\"border-collapse:collapse;vertical-align:top\">\n" +
                "\n" +
                "\t\t\t\t\t<br>\n" +
                "\n" +
                "\t\t\t\t\t<p style=\"font-size:12px;color:#555;line-height:19px;font-weight:300;margin:0 30px;text-align:center\">\n" +
                "\t\t\t\t\t\tThis system email was sent to <span class=\"notranslate\">"+user.getNickName()+"</span> (<span class=\"notranslate\"><a href=\""+user.getGmail()+"\" style=\"color:#555;font-weight:300;text-decoration:none\" target=\"_blank\">"+user.getGmail()+"</a></span>) <span></span>regarding your 4T Account\n" +
                "\t\t\t\t\t\t<br>by 4T, DH16DT, NLU, Ho Chi Minh city\n" +
                "\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t<br>\n" +
                "\n" +
                "\t\t\t\t\t<p style=\"font-size:14px;color:#555;line-height:16px;font-weight:300;margin:20px 30px;text-align:center\">\n" +
                "\t\t\t\t\t\tIf you have any questions, simply respond to this email and we'll be happy to help.\n" +
                "\t\t\t\t\t\t</p>\n" +
                "\n" +
                "\t\t\t\t</td></tr></tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t</td></tr></tbody>\n" +
                "\t\t</table>\n" +
                "\n" +
                "\t</td></tr></tbody>\n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<img src=\"https://ci5.googleusercontent.com/proxy/lx35MxpF8xhHCRmzU_XiaMD5i4nW3bUKi0yQHVPR2P2VnNpsR4zfLzswf5LomJV0sNkq4FCWiny-qJD6n_k24neFrjEpCyrfcf92TbW3mOfwhPtrVZRrxrmvVncBOKcJe1JcHqhN5nkatJ_g6pvpbjcbxE1fpepQULasYO1x2x1sOMo0GRMMcq39C_9xzC8Avcy0-1UY8uxtOV2gDzwQMqsMFlbfwLvc4Cv8xypaB5BpynHJgAnvAxuljOgYfAleAn7ntGuVH-NwUObLPkrYZsBQaS2XimjzfV9L39L8TSdtOiVB9m_wuLCMDdJxLtaaQwOf6cYdrs-r_ht2W93CNABOilUhg1vYFdIKpzpC5y77rnYccvRMV8E_5mB5_7lo1YV1Vwpwi3roycn2EtHAYpccvNmBVk5TWNZ3FfolpmgFFxvFmZb2kONZZh5v9SVdAPAMO2y8jiOlGN7SQSDJSYzRExw5Bw=s0-d-e1-ft#https://links.twiliocdn.com/wf/open?upn=C-2BR1nQLwx9EnFchUAUYdvcD1iPRWM3940mvfArlUqx6mnMGLQJ5YmI3h8AO-2FWgo4msdm6eD5sZEXSaSbM5gpOUZr90xESTmml6bIfNI7WeAuCBkKSoGpzzaoSW5YSX5-2FLX9ckbh9LbgIominaHH0nMIoQjSYbmwtVGLa0d-2BAyOAb5FCKuf49L-2FtL7a0poJ0dPgCIuVgLBiD7fLb2hJLvKw1BKgEYu2lsO2-2Bva6hS31HpyClbryTXc9-2FS9Z98ugc3qFqEwDxQVMvUpYvjtmNy0g-3D-3D\" alt=\"\" width=\"1\" height=\"1\" border=\"0\" style=\"height: 1px !important; width: 1px !important; border-width: 0px !important; margin: 0px !important; padding: 0px !important; display: none !important;\" class=\"CToWUd\" hidden=\"\"><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "</div></div>";
        helper.setText(text, true);

        javaMailSender.send(msg);


    }
}
