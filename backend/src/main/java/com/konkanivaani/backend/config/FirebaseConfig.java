package com.konkanivaani.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/** Creates the Firestore connection once; Spring shares it (singleton bean). */
@Configuration
@Profile("!memory")
public class FirebaseConfig {

    @Bean
    public Firestore firestore() throws IOException {
        // try-with-resources closes the stream automatically
        try (InputStream in = new ClassPathResource("firebase-service-account.json").getInputStream()) {
            if (FirebaseApp.getApps().isEmpty()) {          // initialise only once
                FirebaseOptions options = FirebaseOptions.builder()   // Builder pattern
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .build();
                FirebaseApp.initializeApp(options);
            }
        }
        return FirestoreClient.getFirestore();
    }
}