package com.localvc.commands;

import com.localvc.core.FileScanner;
import com.localvc.core.Index;
import picocli.CommandLine.Command;

import java.nio.file.Path;
import java.util.Map;

@Command(
        name = "index",
        description = "Scan files and update LocalVC index"
)
public class IndexCommand implements Runnable {

    @Override
    public void run() {
        try {
            Map<Path, String> scan =
                    FileScanner.scanAndHash(Path.of("."));

            Index.write(scan);

            System.out.println("Index updated (" + scan.size() + " files).");

        } catch (Exception e) {
            System.out.println("Index failed: " + e.getMessage());
        }
    }
}
