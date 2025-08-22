package com.Project.ContactForm.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class FirebaseService {

    @PostConstruct
    public void init() throws Exception {
        if (FirebaseApp.getApps().isEmpty()) {
            // Load from environment variable (the JSON you pasted in IntelliJ)
            String firebaseJson = System.getenv("FIREBASE_CREDENTIALS");

            if (firebaseJson == null) {
                throw new IllegalStateException("FIREBASE_CREDENTIALS environment variable is not set!");
            }

            InputStream serviceAccount = new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("✅ Firebase initialized successfully!");
        }
    }

    // 🔹 This is what was missing
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}
