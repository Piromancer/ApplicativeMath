package com.company.enthropy;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter file name:");
        String fileName = in.nextLine();
        EnthropyCounter counter = new EnthropyCounter(new File(fileName));
        NumberFormat nf = new DecimalFormat("#0.0000");
        System.out.println("Enthropy is " + counter.countEnthropy() + " bits");
    }
}
