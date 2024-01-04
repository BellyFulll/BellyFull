package com.example.bellyfull.modules.Authentication.Fragments;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import android.os.AsyncTask;

public class EmailSender {

    public static void sendEmail(String toEmail, String verificationCode) {
        new SendEmailTask().execute(toEmail, verificationCode);
    }

    private static class SendEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String toEmail = params[0];
            String verificationCode = params[1];

            final String username = "bellyfull2024@gmail.com";
            final String password = "whvzhhcyavvanixh";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Verification Code");
                message.setText("Your verification code is: " + verificationCode);

                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}
