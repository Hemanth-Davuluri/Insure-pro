package com.insurePro.document_service.Utilites;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.HexFormat;

@Component
public class HashUtil {

    public String generateSHA256(File file)
            throws Exception {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        byte[] hashBytes = digest.digest(fileBytes);
        return HexFormat.of()
                .formatHex(hashBytes);
    }
}
