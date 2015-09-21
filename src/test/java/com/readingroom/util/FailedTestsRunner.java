package com.readingroom.util;

import com.readingroom.util.common.Log;
import org.testng.TestNG;
import java.io.File;
import java.util.Arrays;

public class FailedTestsRunner {

    private static final String FAILED_TESTS_PATH = "target/surefire-reports/testng-failed.xml";
    private static final String OUTPUT_DIR = "target/surefire-reports/";

    public static void main(String[] args) {
        File failedTestsXml = new File(FAILED_TESTS_PATH);
        if (failedTestsXml.exists()) {
            TestNG testNG = new TestNG();
            testNG.setTestSuites(Arrays.asList(failedTestsXml.getPath()));
            testNG.setOutputDirectory(OUTPUT_DIR);

            Log.INFO("Start running failed tests once again");

            testNG.run();
        } else {
            Log.INFO("Nothing to run, no failed tests");
        }
    }
}