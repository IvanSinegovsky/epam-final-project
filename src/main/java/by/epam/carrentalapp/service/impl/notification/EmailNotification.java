package by.epam.carrentalapp.service.impl.notification;

import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.CarServiceImpl;
import by.epam.carrentalapp.service.impl.notification.email.Email;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailNotification {
    private static final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

/*    private static final String EMAIL_CONFIG_PATH = "email";
    private static ResourceBundle resourceBundle;
    private static Properties properties;

    private static String fromUsername;
    private static String fromPassword;

    public static void sendMessage(Email email, User user) {
        if (resourceBundle == null) {
            setEmailProperties();
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUsername, fromPassword);
            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromUsername));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject(email.getMessageSubject());
            message.setText(email.getMessageText(user.getName()));

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Cannot send email to customer");
            throw new ServiceException(e);
        }
    }

    private static void setEmailProperties() {
        try {
            resourceBundle = ResourceBundle.getBundle(EMAIL_CONFIG_PATH);
            properties = System.getProperties();

            properties.put("mail.smtp.host", resourceBundle.getString("mail.smtp.host"));
            properties.put("mail.smtp.port", resourceBundle.getString("mail.smtp.port"));
            properties.put("mail.smtp.ssl.enable", resourceBundle.getString("mail.smtp.ssl.enable"));
            properties.put("mail.smtp.auth", resourceBundle.getString("mail.smtp.auth"));
            properties.put("mail.smtp.user", resourceBundle.getString("mail.smtp.user"));
            properties.put("mail.smtp.password", resourceBundle.getString("mail.smtp.password"));

            fromUsername = resourceBundle.getString("mail.smtp.user");
            fromPassword = resourceBundle.getString("mail.smtp.password");
        } catch (MissingResourceException e) {
            LOGGER.error("Email notification emailPropertiesInit(). Cannot read valid file by properties file path");
            throw new ServiceException(e);
        }
    }*/

    public static void sendMessage(Email email, User user) {
        String to = user.getEmail();
        String from = "isinegovsky@gmail.com";
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("isinegovsky@gmail.com", "");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(email.getMessageSubject());
            message.setText(email.getMessageText(user.getName()));

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}