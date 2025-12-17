package com.localvc.commands;

import com.localvc.core.FileScanner;
import picocli.CommandLine.Command;

import java.nio.file.Path;
import java.util.Map;

@Command(
        name = "scan",
        description = "Scan working directory and compute file hashes"
)
public class ScanCommand implements Runnable {

    @Override
    public void run() {
        try {
            Map<Path, String> results =
                    FileScanner.scanAndHash(Path.of("."));

            results.forEach((path, hash) ->
                    System.out.println(path + " -> " + hash));

        } catch (Exception e) {
            System.out.println("Scan failed: " + e.getMessage());
        }
    }
}
