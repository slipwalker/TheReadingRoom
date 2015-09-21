package com.readingroom.listeners;

import com.readingroom.util.common.Log;
import com.readingroom.util.driver.DriverFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        Log.INFO("Following test is started: " + testContext.getName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        Log.INFO("Following test has been finished: " + testContext.getName());
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        Log.INFO("Following test method has been SKIPPED: " + tr.getMethod().getMethodName(), true);
    }
}