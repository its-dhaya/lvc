package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class SyncService {

    private static final Path LOCAL_LVC = Path.of(".lvc");
    private static final Path LOCAL_COMMITS = LOCAL_LVC.resolve("commits");
    private static final Path LOCAL_HEAD = LOCAL_LVC.resolve("HEAD");

    public static void sync(Path remoteRoot) throws IOException {
        if (!Files.exists(LOCAL_COMMITS)) {
            throw new IOException("No local commits to sync. Run commit first.");
        }

        Path REMOTE_LVC = remoteRoot.resolve("lvc");
        Path REMOTE_COMMITS = REMOTE_LVC.resolve("commits");
        Path REMOTE_HEAD = REMOTE_LVC.resolve("HEAD");

        Files.createDirectories(REMOTE_COMMITS);

        // Collect commit IDs
        Set<String> localIds = listCommitIds(LOCAL_COMMITS);
        Set<String> remoteIds = listCommitIds(REMOTE_COMMITS);

        // PUSH: local -> remote
        for (String id : localIds) {
            if (!remoteIds.contains(id)) {
                copyDir(LOCAL_COMMITS.resolve(id), REMOTE_COMMITS.resolve(id));
                System.out.println("Pushed commit " + id);
            }
        }

        // PULL: remote -> local
        for (String id : remoteIds) {
            if (!localIds.contains(id)) {
                copyDir(REMOTE_COMMITS.resolve(id), LOCAL_COMMITS.resolve(id));
                System.out.println("Pulled commit " + id);
            }
        }

        // HEAD handling (safe)
        if (Files.exists(LOCAL_HEAD) && !Files.exists(REMOTE_HEAD)) {
            Files.copy(LOCAL_HEAD, REMOTE_HEAD);
        } else if (!Files.exists(LOCAL_HEAD) && Files.exists(REMOTE_HEAD)) {
            Files.copy(REMOTE_HEAD, LOCAL_HEAD);
        }

        System.out.println("Sync complete.");
    }

    private static Set<String> listCommitIds(Path dir) throws IOException {
        Set<String> ids = new HashSet<>();
        if (!Files.exists(dir)) return ids;

        try (var stream = Files.list(dir)) {
            stream.filter(Files::isDirectory)
                  .forEach(p -> ids.add(p.getFileName().toString()));
        }
        return ids;
    }

    private static void copyDir(Path src, Path dest) throws IOException {
        Files.walk(src).forEach(source -> {
            try {
                Path target = dest.resolve(src.relativize(source));
                if (Files.isDirectory(source)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
