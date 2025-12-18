package com.localvc.commands;

import com.localvc.core.SyncService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;

@Command(
        name = "sync",
        description = "Sync commits with a shared folder"
)
public class SyncCommand implements Runnable {

    @Parameters(
            index = "0",
            description = "Path to shared sync folder"
    )
    private Path remotePath;

    @Override
    public void run() {
        try {
            SyncService.sync(remotePath);
        } catch (Exception e) {
            System.out.println("Sync failed: " + e.getMessage());
        }
    }
}
