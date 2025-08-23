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

    // ✅ This should be your verified sender in SendGrid (must match SendGrid verified email/domain)
    @Value("${sendgrid.sender.email}")
    private String senderEmail;

    public String sendEmail(EmailRequest emailRequest) {
        try {
            // FROM: your verified sender (must match SendGrid verified email)
            Email from = new Email(senderEmail);

            // TO: recipient entered in form
            Email to = new Email(emailRequest.getRecipientEmail());

            // SUBJECT + BODY
            String subject = emailRequest.getSubject();
            Content content = new Content("text/plain", emailRequest.getBody());

            // Create Mail object
            Mail mail = new Mail(from, subject, to, content);

            // Set Reply-To (so replies go back to the user’s email)
            if (emailRequest.getUserEmail() != null && !emailRequest.getUserEmail().isEmpty()) {
                Email replyTo = new Email(emailRequest.getUserEmail());
                mail.setReplyTo(replyTo);
            }

            // Send email using SendGrid API
            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("📨 Email sent! Status code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
            System.out.println("Response headers: " + response.getHeaders());

            return "✅ Email sent successfully! Status: " + response.getStatusCode();

        } catch (IOException ex) {
            ex.printStackTrace();
            return "❌ Error sending email: " + ex.getMessage();
        }
    }
}
