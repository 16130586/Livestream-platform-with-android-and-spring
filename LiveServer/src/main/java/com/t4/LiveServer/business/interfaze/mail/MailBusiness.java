package com.t4.LiveServer.business.interfaze.mail;

import javax.mail.MessagingException;

public interface MailBusiness {
    String sendMailForgot(String toEmail, String subject, int userId) throws MessagingException;
}
