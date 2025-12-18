package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommitLogReader {

    private static final Path COMMITS_DIR = Path.of(".lvc", "commits");

    public static List<CommitEntry> readLog() throws IOException {
        if (!Files.exists(COMMITS_DIR)) {
            return List.of();
        }

        return Files.list(COMMITS_DIR)
                .filter(Files::isDirectory)
                .map(path -> path.getFileName().toString())
                .sorted(Comparator.reverseOrder())
                .map(id -> {
                    try {
                        String message = Files.readString(
                                COMMITS_DIR.resolve(id).resolve("message.txt")
                        );
                        return new CommitEntry(id, message);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }
}
