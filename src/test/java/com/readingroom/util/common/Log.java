package com.readingroom.util.common;

import com.readingroom.util.driver.DriverFactory;
import org.apache.log4j.Logger;

public class Log {

    public static void INFO(String message) {
        Logger.getLogger(Log.class).info(message);
    }

    public static void INFO(String message, boolean withDriverSession) {
        Logger.getLogger(Log.class).info(withDriverSession ? DriverFactory.getDriverSession().getDriverSession() + ": " + message : message);
    }

    public static void DEBUG(String message) {
        Logger.getLogger(Log.class).debug(message);
    }
}