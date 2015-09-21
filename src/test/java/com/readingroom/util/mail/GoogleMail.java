package com.readingroom.util.mail;

import com.sun.mail.smtp.SMTPTransport;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

public class GoogleMail {

    private GoogleMail() {
    }

    public static void Send(final String username, final String password, String recipientEmail, String title, String message) throws AddressException, MessagingException {
        Send(username, password, recipientEmail, "", title, message, "");
    }

    public static void Send(final String username, final String password, String recipientEmail, String title, String message, String attachmentSource) throws AddressException, MessagingException {
        Send(username, password, recipientEmail, "", title, message, attachmentSource);
    }

    public static void Send(final String username, final String password, String recipientEmail, String ccEmail, String title, String message, String attachmentFileSource) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtps.auth", "true");

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        final MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

        if (ccEmail.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
        }

        msg.setSubject(title);
        //msg.setText(message, "utf-8");
        msg.setSentDate(new Date());

        // message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(message);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        if (attachmentFileSource.length() > 0) {
            // attachment part
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentFileSource);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(source.getName());

            multipart.addBodyPart(messageBodyPart);
        }

        msg.setContent(multipart);

        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

        t.connect("smtp.gmail.com", username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
    }
}