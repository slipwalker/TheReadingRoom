package com.readingroom.listeners;

import com.readingroom.util.common.Log;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import static com.readingroom.util.reporter.Reporter.*;

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
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        Log.INFO("Test SUCCESS: " + result.getName());
        reportScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        Log.INFO("Following test method has been SKIPPED: " + tr.getMethod().getMethodName(), true);
        reportScreenshot();
    }
}