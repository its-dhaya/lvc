package com.localvc.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {

    private static final Path INDEX_PATH = Path.of(".lvc", "index");

    public static void write(Map<Path, String> entries) throws IOException {
        if (!Files.exists(INDEX_PATH.getParent())) {
            throw new IOException("Not a LocalVC repository (run lvc init)");
        }

        List<String> lines = entries.entrySet().stream()
                .map(e -> e.getKey() + "|" + e.getValue())
                .toList();

        Files.write(INDEX_PATH, lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static Map<Path, String> read() throws IOException {
        Map<Path, String> entries = new HashMap<>();

        if (!Files.exists(INDEX_PATH)) {
            return entries;
        }

        List<String> lines = Files.readAllLines(INDEX_PATH);
        for (String line : lines) {
            String[] parts = line.split("\\|", 2);
            if (parts.length == 2) {
                entries.put(Path.of(parts[0]), parts[1]);
            }
        }

        return entries;
    }
}
