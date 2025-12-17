package com.localvc;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import com.localvc.commands.InitCommand;

@Command(
        name = "lvc",
        description = "LocalVC - a lightweight local version control tool",
        mixinStandardHelpOptions = true,
        subcommands = { InitCommand.class }
)

public class App implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Use 'lvc init' to initialize a repository.");

    }
}
