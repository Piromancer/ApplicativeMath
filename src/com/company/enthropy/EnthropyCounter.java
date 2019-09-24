package com.company.enthropy;

import com.company.util.Pair;

import java.io.*;
import java.util.HashMap;

public class EnthropyCounter {

    private File input;
    private static HashMap<Character, Long> collectedData = new HashMap<>();
    private static HashMap<Pair<Character, Character>, Long> collectedPairData = new HashMap<>();
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
        String temp = POSSIBLE_SIMBOLS.toString();
        for(int i = 0; i < temp.length(); i++){
            for (int j = 0; j < temp.length(); j++){
                collectedPairData.put(new Pair<>(temp.charAt(i), temp.charAt(j)), 0L);
            }
        }
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

    public double countPairEnthropy() {
        try (
                Reader fis = new FileReader(input);
                BufferedReader bis = new BufferedReader(fis);
        ) {
            int currentChar, previousChar;
            long totalSymbols = 1;
            previousChar = bis.read();
            currentChar = bis.read();
            while(currentChar != -1){
                char character1 = Character.toLowerCase((char) previousChar), character2 = Character.toLowerCase((char) currentChar);
                if(OTHER_SYMBOLS.indexOf(character1) != -1) character1 = '*';
                if(OTHER_SYMBOLS.indexOf(character2) != -1) character2 = '*';
                if(collectedData.containsKey(character2)){
                    totalSymbols++;
                }
                if(!collectedData.containsKey(character1)) {
                    previousChar = currentChar;
                    currentChar = bis.read();
                    continue;
                }
                if(!collectedData.containsKey(character2)) {
                    previousChar = currentChar;
                    currentChar = bis.read();
                    continue;
                }
                collectedPairData.compute(new Pair<>(character1, character2), (k,v) -> v+1);
                previousChar = currentChar;
                currentChar = bis.read();
            }
            double total = 0;
            for (var i : collectedPairData.keySet()){
                if(collectedPairData.get(i) == 0) {
                    continue;
                }
                double pij = (double) collectedPairData.get(i) / totalSymbols;
                double pj = (double) collectedData.get(i.getRight()) / totalSymbols;
                total += pij * pj * Math.log(pij) / Math.log(2);
            }
            return -total;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return -1;
    }
}
