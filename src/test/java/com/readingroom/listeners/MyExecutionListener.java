package com.readingroom.listeners;

import com.readingroom.util.mail.GoogleMail;
import com.readingroom.util.common.Log;
import com.readingroom.util.common.PropertyLoader;
import org.testng.IExecutionListener;
import javax.mail.MessagingException;

public class MyExecutionListener implements IExecutionListener {

    private long startTime;

    @Override
    public void onExecutionStart() {
        startTime = System.currentTimeMillis();
        Log.INFO("TestNG is going to start");
        try {
            GoogleMail.Send(
                    PropertyLoader.loadProperty("gmail.email"),
                    PropertyLoader.loadProperty("gmail.password"),
                    PropertyLoader.loadProperty("recipient.to"),
                    "TestNG Runner Notification", "TestNG is going to start for TheReadingRoom project");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onExecutionFinish() {
        Log.INFO("TestNG has finished work, took around " + (System.currentTimeMillis() - startTime) / 1000 + " sec");
        try {
            GoogleMail.Send(
                    PropertyLoader.loadProperty("gmail.email"),
                    PropertyLoader.loadProperty("gmail.password"),
                    PropertyLoader.loadProperty("recipient.to"),
                    "TestNG Runner Notification", "TestNG has finished work for TheReadingRoom project. Tests log has been attached.",
                    PropertyLoader.loadProperty("test.log.path"));
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}