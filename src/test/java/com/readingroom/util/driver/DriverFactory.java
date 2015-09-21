package com.readingroom.util.driver;

import com.readingroom.util.EntitiesContainer;
import com.readingroom.util.common.Log;
import com.readingroom.webdriver.Browser;
import com.readingroom.webdriver.ExtendedWebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DriverFactory {

    public static final ConcurrentMap<Long, DriverSession> driverSession = new ConcurrentHashMap<>();

    public static ExtendedWebDriver initDriver(String browser, String tempFolder, String testName) {

        ExtendedWebDriver driver;
        Long threadId = Thread.currentThread().getId();

        if (driverSession.containsKey(threadId)) {
            driver = driverSession.get(threadId).getDriver();
            Log.INFO("Driver already started for thread: " + threadId);
        } else {
            driver = getDriver(browser, tempFolder);
            String sessionId = ((RemoteWebDriver) driver.getBrowserDriver()).getSessionId().toString();
            driverSession.put(threadId, new DriverSession(sessionId, driver, testName));
            Log.INFO("Start Driver for thread: " + threadId);
        }

        Log.INFO("Session start: " + driverSession.get(threadId).getDriverSession());

        //possible to return a driver from DriverSession
        return driver;
    }

    public static DriverSession getDriverSession() {
        return driverSession.get(Thread.currentThread().getId());
    }

    public static void quitAllDrivers() {
        for (DriverSession session : driverSession.values())
            session.getDriver().quit();
        Log.INFO("Quit all drivers");
    }

    public static void quitDriver() {
        Long threadId = Thread.currentThread().getId();
        if (driverSession.containsKey(threadId)) {
            DriverSession session = driverSession.remove(threadId);
            session.getDriver().quit();
            EntitiesContainer.removeAll();
            Log.INFO("Quit driver for thread:" + threadId);
        }
    }

    private static ExtendedWebDriver getDriver(String browser, String tempFolder) {
        return new ExtendedWebDriver(Browser.getByValue(browser), new File(tempFolder));
    }
}