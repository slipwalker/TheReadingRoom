package com.readingroom.util.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {

    public static BufferedReader getBufferedReaderOFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            return new BufferedReader(fileReader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        throw new IllegalArgumentException("Wrong file path: " + filePath);
    }
}