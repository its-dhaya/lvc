package com.localvc.commands;

import com.localvc.core.CommitEntry;
import com.localvc.core.CommitLogReader;
import picocli.CommandLine.Command;

@Command(
        name = "log",
        description = "Show commit history"
)
public class LogCommand implements Runnable {

    @Override
    public void run() {
        try {
            var entries = CommitLogReader.readLog();

            if (entries.isEmpty()) {
                System.out.println("No commits found.");
                return;
            }

            for (CommitEntry entry : entries) {
                System.out.println(entry.id() + " - " + entry.message());
            }
        } catch (Exception e) {
            System.out.println("Failed to read log: " + e.getMessage());
        }
    }
}
