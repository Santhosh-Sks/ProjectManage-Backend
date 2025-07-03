package org.project.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String fromEmail) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
    }

    public void sendTaskAssignmentEmail(String toEmail, String taskTitle) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("New Task Assigned: " + taskTitle);

            String content = getTaskAssignmentEmailContent(taskTitle);
            helper.setText(content, true); // true enables HTML

            mailSender.send(message);
            System.out.println("Task assignment email sent to " + toEmail);
        } catch (MessagingException | MailException e) {
            System.err.println("Failed to send task assignment email to " + toEmail);
            e.printStackTrace();
        }
    }

    public void sendInvitationEmail(String toEmail, String projectId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("You're Invited to Collaborate on a Project!");

            String content = getInvitationEmailContent(projectId);
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("Invitation email sent successfully to " + toEmail);
        } catch (MessagingException | MailException e) {
            System.err.println("Failed to send invitation email to " + toEmail);
            e.printStackTrace();
        }
    }

    public String sendOTPEmail(String toEmail, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your One-Time Password (OTP)");

            String content = "<html><body>" +
                    "<h2 style='color:#2E86C1;'>Your OTP Code</h2>" +
                    "<p>Use the following OTP to verify your email:</p>" +
                    "<h3 style='color:#E74C3C;'>" + otpCode + "</h3>" +
                    "<p>This OTP is valid for 5 minutes.</p>" +
                    "</body></html>";

            helper.setText(content, true);
            mailSender.send(message);

            return "OTP sent successfully to " + toEmail;
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
            return "Failed to send OTP to " + toEmail;
        }
    }

       private String getTaskAssignmentEmailContent(String taskTitle) {
        return "<html><body style='font-family: Arial, sans-serif; background-color:#f4f6f8; padding:20px;'>" +
                "<div style='max-width:600px; margin: auto; background:#ffffff; padding:20px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1);'>" +
                "<h2 style='color:#28B463; border-bottom:1px solid #e0e0e0; padding-bottom:10px;'>📌 New Task Assigned</h2>" +
                "<p style='font-size:16px; color:#333;'>Hello,</p>" +
                "<p style='font-size:16px; color:#333;'>You've been assigned a new task:</p>" +
                "<p style='font-size:18px; font-weight:bold; color:#2C3E50; margin:10px 0;'>" + taskTitle + "</p>" +
                "<p style='font-size:16px; color:#333;'>Please log in to your dashboard to view more details and begin working on it.</p>" +
                "<div style='margin-top:20px;'><a href='https://projectstack-sks.vercel.app/dashboard' style='display:inline-block; background:#28B463; color:white; padding:10px 20px; border-radius:5px; text-decoration:none;'>Go to Dashboard</a></div>" +
                "<hr style='margin:30px 0;'>" +
                "<p style='font-size:12px; color:#888;'>If you weren't expecting this email, you can safely ignore it.</p>" +
                "</div></body></html>";
    }
    
private String getInvitationEmailContent(String projectId) {
    String projectLink = "https://projectstack-sks.vercel.app/projects/" + projectId;

    return "<html><body style='font-family: Arial, sans-serif; background-color:#f4f6f8; padding:20px;'>" +
            "<div style='max-width:600px; margin: auto; background:#ffffff; padding:20px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.1);'>" +
            "<h2 style='color:#2E86C1; border-bottom:1px solid #e0e0e0; padding-bottom:10px;'>📬 Project Invitation</h2>" +
            "<p style='font-size:16px; color:#333;'>You have been invited to join a project!</p>" +
            "<p style='font-size:16px; color:#333;'>Click the button below to view and accept the invitation:</p>" +
            "<div style='margin:20px 0;'><a href='" + projectLink + "' style='display:inline-block; background:#2E86C1; color:white; padding:10px 20px; border-radius:5px; text-decoration:none;'>Join Project</a></div>" +
            "<p style='font-size:14px; color:#555;'>If you do not recognize this invitation, feel free to ignore this email.</p>" +
            "<hr style='margin:30px 0;'>" +
            "<p style='font-size:12px; color:#888;'>This is an automated message. Please do not reply.</p>" +
            "</div></body></html>";
}
    public void sendEmail(String toEmail, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message, false); // false means plain text

            mailSender.send(mimeMessage);
            System.out.println("Email sent to " + toEmail);
        } catch (MessagingException | MailException e) {
            System.err.println("Failed to send email to " + toEmail);
            e.printStackTrace();
        }
    }

}
