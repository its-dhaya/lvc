package com.localvc.core;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StatusChecker {

    public static StatusResult compare(
            Map<Path, String> index,
            Map<Path, String> current
    ) {
        Set<Path> unchanged = new HashSet<>();
        Set<Path> modified = new HashSet<>();
        Set<Path> added = new HashSet<>();
        Set<Path> deleted = new HashSet<>();

        for (Path path : index.keySet()) {
            if (!current.containsKey(path)) {
                deleted.add(path);
            } else if (index.get(path).equals(current.get(path))) {
                unchanged.add(path);
            } else {
                modified.add(path);
            }
        }

        for (Path path : current.keySet()) {
            if (!index.containsKey(path)) {
                added.add(path);
            }
        }

        return new StatusResult(unchanged, modified, added, deleted);
    }
}
