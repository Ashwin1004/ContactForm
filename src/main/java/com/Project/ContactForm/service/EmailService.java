package com.Project.ContactForm.service;

import com.Project.ContactForm.model.EmailRequest;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender.email}")
    private String senderEmail;

    public String sendEmail(EmailRequest emailRequest) {
        Email from = new Email(senderEmail);
        String subject = emailRequest.getSubject();
        Email to = new Email(emailRequest.getRecipientEmail());
        Content content = new Content("text/plain", emailRequest.getBody());

        Mail mail = new Mail(from, subject, to, content);

        // Set Reply-To as the user’s email
        Email replyTo = new Email(emailRequest.getUserEmail());
        mail.setReplyTo(replyTo);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Email sent! Status code: " + response.getStatusCode());
            return "Email sent successfully! Status: " + response.getStatusCode();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error sending email: " + ex.getMessage();
        }
    }
}
