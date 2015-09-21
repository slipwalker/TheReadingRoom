package com.readingroom.util;

import com.readingroom.models.User;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class EntitiesContainer {

    private static ThreadLocal<ExtendedWebDriver> currentDriver = new ThreadLocal<>();
    private static ThreadLocal<User> currentUser = new ThreadLocal<>();
    private static ThreadLocal<PageManager> currentPageManager = new ThreadLocal<>();

    public static void init(ExtendedWebDriver drv) {
        if (currentDriver.get() == null)
            currentDriver.set(drv);
    }

    public static void init(ExtendedWebDriver drv, User usr) {
        if (currentDriver.get() == null)
            currentDriver.set(drv);
        if (currentUser.get() == null)
            currentUser.set(usr);
    }

    public static ExtendedWebDriver getExtendedDriver() {
        return currentDriver.get();
    }

    public static WebDriver getBrowserDriver() {
        return getExtendedDriver().getBrowserDriver();
    }

    public static JavascriptExecutor getExecutor() {
        return getExtendedDriver().getJavascriptExecutor();
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static PageManager getPageManager() {
        PageManager pm = currentPageManager.get();
        if (pm == null) {
            try {
                pm = new PageManager(new URL(App.getAppUrl()));
                currentPageManager.set(pm);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        return pm;
    }

    public static void removeAll() {
        removeDriver();
        removeUser();
        removePageManager();
    }

    public static void removeDriver() {
        currentDriver.remove();
    }

    public static void removeUser() {
        currentUser.remove();
    }

    public static void removePageManager() {
        currentPageManager.remove();
    }
}