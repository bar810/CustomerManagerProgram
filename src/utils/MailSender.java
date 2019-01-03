package utils;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import static model.GlobalProperties._logger;
import static model.GlobalProperties.getProperty;
import static utils.Constants.MAIL_PASSWORD;
import static utils.Constants.MAIL_USER_NAME;

/**
 * @author bbrownsh
 * @since 12/23/2018
 */
public class MailSender {
    private static final String senderEmail = getProperty(MAIL_USER_NAME);
    private static final String senderPassword = getProperty(MAIL_PASSWORD);
    /*
    can be sent as HTML. for more details can take a loo here:
    https://www.logicbig.com/tutorials/java-ee-tutorial/java-mail/java-mail-quick-example.html
     */
    public static void sendAsHtml(String to, String title, String html,String attachmentPath) {
        _logger.debug("Sending email to " + to);
        try {
            Session session = createSession();
            //create message using session
            MimeMessage message = new MimeMessage(session);
            prepareEmailMessage(message, to, title, html,attachmentPath);
            //sending message
            Transport.send(message);
            _logger.debug("mail sent successfully");
        } catch (Exception ex) {
            _logger.error("Error with sending mail");
            }

    }
    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html,String attachmentPath) throws javax.mail.MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
        if(attachmentPath!=null &&!attachmentPath.isEmpty()){
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Part two is attachment
            BodyPart  messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentPath);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
        }
    }
    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");//Outgoing server requires authentication
        props.put("mail.smtp.starttls.enable", "true");//TLS must be activated
        props.put("mail.smtp.host", "smtp.gmail.com"); //Outgoing server (SMTP) - change it to your SMTP server
        props.put("mail.smtp.port", "587");//Outgoing port

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
