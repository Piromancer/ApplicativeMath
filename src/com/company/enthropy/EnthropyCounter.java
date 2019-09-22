package com.company.enthropy;

import java.io.*;
import java.util.HashMap;

public class EnthropyCounter {

    private File input;
    private static HashMap<Character, Long> collectedData = new HashMap<>();
    private static final StringBuilder POSSIBLE_SIMBOLS = new StringBuilder();
    private static final String OTHER_SYMBOLS = ".!?;:-\'\"()\\/{}#@";

    static {
        for (char i = 'a'; i <= 'z'; i++) {
            POSSIBLE_SIMBOLS.append(i);
        }
        for (byte i = 0; i <= 9; i++) {
            POSSIBLE_SIMBOLS.append(i);
        }
        POSSIBLE_SIMBOLS
                .append('*')
                .append(' ');
    }

    public EnthropyCounter(File input) {
        this.input = input;
        POSSIBLE_SIMBOLS
                .toString()
                .chars()
                .forEach((symbol) -> collectedData.put((char) symbol, 0L));
    }

    public void setInput(File input) {
        this.input = input;
    }

    public double countEnthropy() {
        try (
                Reader fis = new FileReader(input);
                BufferedReader bis = new BufferedReader(fis);
        ) {
            int currentChar;
            long totalSymbols = 0;
            currentChar = bis.read();
            while (currentChar != -1) {
                char character = (char) currentChar;
                if (OTHER_SYMBOLS.indexOf(character) == -1 && collectedData.containsKey(character)) {
                    collectedData.compute(Character.toLowerCase(character), (k, v) -> (v == null) ? 1 : v + 1);
                    totalSymbols++;
                } else if (OTHER_SYMBOLS.indexOf(character) != -1) {
                    collectedData.compute('*', (k, v) -> v + 1);
                    totalSymbols++;
                }
                currentChar = bis.read();
            }
            double total = 0;
            for (long i : collectedData.values()){
                if (i == 0) continue;
                double p = (double) i / totalSymbols;
                total += p * Math.log(p) / Math.log(2);
            }
            return -total;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return -1;
    }
}
