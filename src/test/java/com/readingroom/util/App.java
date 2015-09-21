package com.readingroom.util;

public class App {

    private static String appUrl;
    private static String browserName;
    private static String downloadFolder;

    public static void init(String url, String browser, String defaultDownloadFolder) {
        appUrl = url;
        browserName = browser;
        downloadFolder = defaultDownloadFolder;
    }

    public static String getAppUrl() {
        return appUrl;
    }

    public static String getBrowserName() {
        return browserName;
    }

    public static String getBrowserDownloadFolder() {
        return downloadFolder;
    }
}