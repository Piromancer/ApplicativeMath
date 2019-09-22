package com.company.enthropy;

import java.io.*;
import java.util.HashMap;

public class EnthropyCounter {

    private File input;
    private static HashMap<Character, Long> collectedData = new HashMap<>();
    private static final StringBuilder POSSIBLE_SIMBOLS = new StringBuilder();
    private static final String OTHER_SYMBOLS = ".!?;:-\'\"";

    static {
        for(char i = 'a'; i<='z'; i++){
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
        try(
                Reader fis = new FileReader(input);
                BufferedReader bis = new BufferedReader(fis);
        ){
            int currentChar;
            currentChar = bis.read();
            while (currentChar != -1) {
                char character = (char) currentChar;
                if (OTHER_SYMBOLS.indexOf(character) == -1){
                    collectedData.compute(character, (k,v) -> (v == null) ? 1 : v+1);
                } else{
                    collectedData.compute('*', (k,v) -> v+1);
                }
                currentChar = bis.read();
            }
            return collectedData.get('*');
        }
        catch (IOException ioe){ioe.printStackTrace();}
        return -1;
    }
}
