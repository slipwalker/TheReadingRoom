package com.readingroom.util.common;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import static org.hamcrest.MatcherAssert.assertThat;
import static java.lang.Thread.currentThread;

public class Path {

    private static final String RESOURCE_ROOT_FOLDER = "src/test/resources";

    public static String getFullPath(String relativePath) {
        if (relativePath.isEmpty()) {
            return "";
        } else {
            try {
                return getFileByRelativePath(relativePath).getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static File getFileByRelativePath(String relativePath) {
        String path = RESOURCE_ROOT_FOLDER + "/" + relativePath;
        return new File(path);
    }

    public static String getAbsolutePath(String relativePath) {
        if (relativePath.isEmpty()) return "";
        URL resUrl = Path.class.getClassLoader().getResource(relativePath);

        String path = null;

        if (resUrl == null)
            throw new IllegalArgumentException("No any resources by following path: " + relativePath);

        try {
            path = Paths.get(resUrl.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String getFilePath(String dirPath, String fileName) {
        URL resource = currentThread().getContextClassLoader().getResource(dirPath + fileName);
        try {
            if (resource == null) {
                throw new RuntimeException("Resource " + fileName +
                        " could not be found or the invoker doesn't have adequate privileges to get the resource");
            }
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assertThat("Resource was not found", false);
        return null;
    }

    public static String getFilePath(String fileName) {
        URL resource = currentThread().getContextClassLoader().getResource(fileName);
        try {
            if (resource == null) {
                throw new RuntimeException("Resource " + fileName +
                        " could not be found or the invoker doesn't have adequate privileges to get the resource");
            }
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        assertThat("Resource was not found", false);
        return null;
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