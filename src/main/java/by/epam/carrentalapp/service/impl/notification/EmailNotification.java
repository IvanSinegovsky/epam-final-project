package by.epam.carrentalapp.service.impl.notification;

import by.epam.carrentalapp.bean.entity.user.User;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.CarServiceImpl;
import by.epam.carrentalapp.service.impl.notification.email.Email;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmailNotification {
    private static final Logger LOGGER = Logger.getLogger(CarServiceImpl.class);

    private static final String EMAIL_CONFIG_PATH = "email";
    private static ResourceBundle resourceBundle;
    private static Properties properties;

    private static Session session;

    private static String corporateEmail;
    private static String corporatePassword;

    public static void sendMessage(Email email, User user) {
        String customerEmail = user.getEmail();

        if (corporateEmail == null) {
            propertiesInit();
        }

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(corporateEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));
            message.setSubject(email.getMessageSubject());
            message.setText(email.getMessageText(user.getName()));

            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Cannot send email");
            throw new ServiceException(e);
        }
    }

    private static void propertiesInit() {
        properties = System.getProperties();

        resourceBundle = ResourceBundle.getBundle(EMAIL_CONFIG_PATH);

        corporateEmail = resourceBundle.getString("mail.smtp.user");
        corporatePassword = resourceBundle.getString("mail.smtp.password");

        properties.put("mail.smtp.host", resourceBundle.getString("mail.smtp.host"));
        properties.put("mail.smtp.port", resourceBundle.getString("mail.smtp.port"));
        properties.put("mail.smtp.ssl.enable", resourceBundle.getString("mail.smtp.ssl.enable"));
        properties.put("mail.smtp.auth", resourceBundle.getString("mail.smtp.auth"));

        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(corporateEmail, corporatePassword);
            }
        });

        session.setDebug(true);
    }
}