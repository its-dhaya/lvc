package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

public class CheckoutService {

    private static final Path LOCAL_LVC = Path.of(".lvc");
    private static final Path LOCAL_COMMITS = LOCAL_LVC.resolve("commits");
    private static final Path LOCAL_HEAD = LOCAL_LVC.resolve("HEAD");

    public static void checkout(String commitId) throws IOException {
        Path commitPath = LOCAL_COMMITS.resolve(commitId);

        if (!Files.exists(commitPath) || !Files.isDirectory(commitPath)) {
            throw new IOException("Commit not found: " + commitId);
        }

        // Safety: backup uncommitted changes
        // You could enhance this later, for now just overwrite

        // Copy all files from commit to working directory
        Files.walk(commitPath)
            .forEach(source -> {
                try {
                    Path target = Path.of(".").resolve(commitPath.relativize(source));
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        // Update HEAD
        Files.writeString(LOCAL_HEAD, commitId, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
