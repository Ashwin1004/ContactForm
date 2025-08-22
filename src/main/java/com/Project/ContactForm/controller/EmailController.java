package com.Project.ContactForm.controller;

import com.Project.ContactForm.model.EmailRequest;
import com.Project.ContactForm.service.EmailService;
import com.Project.ContactForm.service.FirebaseService;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private FirebaseService firebaseService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest request) throws Exception {
        // 1️⃣ Send email
        String result = emailService.sendEmail(request);

        // 2️⃣ Save in Firestore
        Firestore db = firebaseService.getFirestore();
        DocumentReference docRef = db.collection("emails").document();
        docRef.set(request);

        return result + " & Stored in Firestore!";
    }
}
