package com.localvc;
import com.localvc.commands.InitCommand;
import com.localvc.commands.ScanCommand;
import com.localvc.commands.IndexCommand;
import com.localvc.commands.StatusCommand;
import com.localvc.commands.CommitCommand;


import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "lvc",
        description = "LocalVC - a lightweight local version control tool",
        mixinStandardHelpOptions = true,
        subcommands = { InitCommand.class, ScanCommand.class, IndexCommand.class, StatusCommand.class, CommitCommand.class }
        
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
