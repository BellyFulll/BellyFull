package com.example.bellyfull.modules.Authentication.Fragments;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        new SendVerificationCodeEmailTask().execute(toEmail, verificationCode);
    }

    public static void sendEmergencyDataEmail(String toEmail, String name, Double latitude, Double longitude, String pregnantWeeks) {
        new SendEmergencyDataEmail().execute(toEmail, name, latitude.toString(), longitude.toString(), pregnantWeeks);
    }

    private static class SendVerificationCodeEmailTask extends AsyncTask<String, Void, Void> {
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
                System.out.println("something wrong happened");
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private static class SendEmergencyDataEmail extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String toEmail = params[0];
            String name = params[1];
            Double latitude = Double.parseDouble(params[2]);
            Double longitude = Double.parseDouble(params[3]);
            String locationLink = generateLocationLink(latitude, longitude);
            String pregnantWeeks = params[4];

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
                message.setSubject("Emergency Data of " + name);
                message.setText("Current Location: " + locationLink + "\nPregnant Weeks: " + pregnantWeeks);

                Transport.send(message);
            } catch (MessagingException e) {
                System.out.println("failure");
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    private static String generateLocationLink(double latitude, double longitude) {
        String urlFormat = "https://www.google.com/maps?q=%f,%f";

        String locationLink = String.format(urlFormat, latitude, longitude);

        return locationLink;
    }
}
