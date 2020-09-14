package by.mishota.graduation.mail;

import by.mishota.graduation.exception.MailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {

    private static final String PATH_MAIL_PROPERTIES = "mail.properties";


    private Properties mailProperties;

    public void sendConfirmationLink(String emailTo, String subject, String message) throws MailException {
        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream(PATH_MAIL_PROPERTIES)) {

            mailProperties = new Properties();

            mailProperties.load(is);
            Session mailSession = Session.getDefaultInstance(mailProperties);
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(new InternetAddress(mailProperties.getProperty("mail.smtps.user")));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message,"text/html");

            Transport transport = mailSession.getTransport();
            transport.connect(null, mailProperties.getProperty("mail.smtps.password"));
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

        } catch (IOException | MessagingException e) {
            throw new MailException("Error sending email", e);
        }
    }
}
