package com.localvc.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.nio.file.*;
import java.io.IOException;

import com.localvc.core.CheckoutService;

@Command(
        name = "checkout",
        description = "Restore files from a commit"
)
public class CheckoutCommand implements Runnable {

    @Parameters(
        index = "0",
        description = "Commit ID to checkout"
    )
    private String commitId;

    @Override
    public void run() {
        try {
            CheckoutService.checkout(commitId);
            System.out.println("Checked out commit " + commitId);
        } catch (Exception e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }
}
