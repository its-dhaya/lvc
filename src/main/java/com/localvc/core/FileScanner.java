package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class FileScanner {

    public static Map<Path, String> scanAndHash(Path root) throws IOException {
        Map<Path, String> fileHashes = new HashMap<>();

        Files.walk(root)
                .filter(Files::isRegularFile)
.filter(path ->
        !path.startsWith(root.resolve(".lvc")) &&
        !path.startsWith(root.resolve(".git")) &&
        !path.startsWith(root.resolve("target")) &&
        !path.startsWith(root.resolve(".mvn"))
)


                .forEach(path -> {
                    try {
                        String hash = hashFile(path);
                        fileHashes.put(root.relativize(path), hash);
                    } catch (Exception e) {
                        System.out.println("Failed to hash file: " + path);
                    }
                });

        return fileHashes;
    }

    private static String hashFile(Path path)
            throws IOException, NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = Files.readAllBytes(path);
        byte[] hashBytes = digest.digest(bytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
