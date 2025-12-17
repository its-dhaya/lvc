package com.localvc.commands;

import com.localvc.core.CommitStore;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "commit",
        description = "Create a new commit"
)
public class CommitCommand implements Runnable {

    @Option(
            names = {"-m", "--message"},
            description = "Commit message",
            required = true
    )
    private String message;

    @Override
    public void run() {
        try {
            String commitId = CommitStore.createCommit(message);
            System.out.println("Committed as " + commitId);
        } catch (Exception e) {
            System.out.println("Commit failed: " + e.getMessage());
        }
    }
}
