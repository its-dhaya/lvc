package com.localvc.commands;

import com.localvc.core.FileScanner;
import com.localvc.core.Index;
import com.localvc.core.StatusChecker;
import com.localvc.core.StatusResult;
import picocli.CommandLine.Command;

import java.nio.file.Path;
import java.util.Map;

@Command(
        name = "status",
        description = "Show working directory status"
)
public class StatusCommand implements Runnable {

    @Override
    public void run() {
        try {
            Map<Path, String> index = Index.read();
            Map<Path, String> current =
                    FileScanner.scanAndHash(Path.of("."));

            StatusResult result =
                    StatusChecker.compare(index, current);

            print(result);

        } catch (Exception e) {
            System.out.println("Status failed: " + e.getMessage());
        }
    }

    private void print(StatusResult r) {
        if (!r.added.isEmpty()) {
            System.out.println("New files:");
            r.added.forEach(p -> System.out.println("  " + p));
        }

        if (!r.modified.isEmpty()) {
            System.out.println("Modified files:");
            r.modified.forEach(p -> System.out.println("  " + p));
        }

        if (!r.deleted.isEmpty()) {
            System.out.println("Deleted files:");
            r.deleted.forEach(p -> System.out.println("  " + p));
        }

        if (r.added.isEmpty() && r.modified.isEmpty() && r.deleted.isEmpty()) {
            System.out.println("Working directory clean.");
        }
    }
}
