package com.localvc.commands;

import picocli.CommandLine.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(
        name = "init",
        description = "Initialize a LocalVC repository"
)
public class InitCommand implements Runnable {

    @Override
    public void run() {
        Path lvcDir = Path.of(".lvc");

        if (Files.exists(lvcDir)) {
            System.out.println("Error: LocalVC repository already initialized.");
            return;
        }

        try {
            Files.createDirectory(lvcDir);

            Files.createFile(lvcDir.resolve("config.properties"));
            Files.createFile(lvcDir.resolve("HEAD"));

            System.out.println("Initialized empty LocalVC repository in " +
                    lvcDir.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error initializing repository: " + e.getMessage());
        }
    }
}
