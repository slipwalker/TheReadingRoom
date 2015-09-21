package com.readingroom.util.common;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Path {

    public static String getAbsoluteFilePath(String fileName) {

        URL res = Path.class.getClassLoader().getResource(fileName);
        String path = null;

        try {
            path = Paths.get(res.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }

    public static String getAbsolutePath() {

        URL res = Path.class.getClassLoader().getResource("application.properties");
        String path = null;

        try {
            path = Paths.get(res.toURI()).toAbsolutePath().getParent().toString() + "\\";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return path;
    }
}