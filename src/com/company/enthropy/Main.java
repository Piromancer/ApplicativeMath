package com.company.enthropy;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter file name:");
        String fileName = in.nextLine();
        EnthropyCounter counter = new EnthropyCounter(new File(fileName));
        System.out.println("Enthropy is " + counter.countEnthropy());
    }
}
