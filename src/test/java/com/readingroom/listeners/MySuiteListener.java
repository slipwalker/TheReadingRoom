package com.readingroom.listeners;

import com.readingroom.util.common.Log;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import java.util.Map;

public class MySuiteListener implements ISuiteListener {

    private static final String FULL_TEST_SUITE_NAME = "Full Test Suite";

    @Override
    public void onStart(ISuite iSuite) {
        if (!iSuite.getName().equals(FULL_TEST_SUITE_NAME))
            Log.INFO(String.format("Suite '%s 'is started", iSuite.getName()));
    }

    @Override
    public void onFinish(ISuite iSuite) {
        if (!iSuite.getName().equals(FULL_TEST_SUITE_NAME)) {
            Map<String, ISuiteResult> results = iSuite.getResults();
            int passed = 0;
            int failed = 0;
            int skipped = 0;
            for (Map.Entry<String, ISuiteResult> entry: results.entrySet()) {
                ITestContext testContext= entry.getValue().getTestContext();
                passed += testContext.getPassedTests().size();
                failed += testContext.getFailedTests().size();
                skipped += testContext.getSkippedTests().size();
            }
            Log.INFO(String.format("Suite '%s' is finished. RESULTS - Passed: %d, Failed: %d, Skipped: %d", iSuite.getName(), passed, failed, skipped));
        }
    }
}