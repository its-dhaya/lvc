package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;

public class CommitStore {

    private static final Path LVC_DIR = Path.of(".lvc");
    private static final Path COMMITS_DIR = LVC_DIR.resolve("commits");
    private static final Path HEAD_FILE = LVC_DIR.resolve("HEAD");
    private static final Path INDEX_FILE = LVC_DIR.resolve("index");

    public static String createCommit(String message) throws IOException {
        if (!Files.exists(INDEX_FILE)) {
            throw new IOException("Nothing to commit (index missing)");
        }

        Files.createDirectories(COMMITS_DIR);

        int nextId = getNextCommitId();
        String commitId = String.format("%04d", nextId);

        Path commitDir = COMMITS_DIR.resolve(commitId);
        Files.createDirectory(commitDir);

        Files.writeString(commitDir.resolve("message.txt"), message);
        Files.copy(INDEX_FILE, commitDir.resolve("snapshot"));

        Files.writeString(HEAD_FILE, commitId);

        return commitId;
    }

    private static int getNextCommitId() throws IOException {
        if (!Files.exists(COMMITS_DIR)) {
            return 1;
        }

        return Files.list(COMMITS_DIR)
                .map(p -> p.getFileName().toString())
                .filter(name -> name.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
    }
}
