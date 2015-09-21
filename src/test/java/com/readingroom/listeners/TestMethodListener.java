package com.readingroom.listeners;

import com.readingroom.util.common.Log;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import static com.readingroom.util.reporter.Reporter.reportErrorScreenshot;

public class TestMethodListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Log.INFO("Following test method is started: " + iInvokedMethod.getTestMethod().getMethodName(), true);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            Boolean success = iTestResult.isSuccess();
            if (!success) reportErrorScreenshot("Error");

            String result = success ? "\t-- PASSED" : "\t-- FAILED";
            String method = iInvokedMethod.getTestMethod().getMethodName();
            Log.INFO("Following test method has been finished: " + method + result, true);
        }
    }
}