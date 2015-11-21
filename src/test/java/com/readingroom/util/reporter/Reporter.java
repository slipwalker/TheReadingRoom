package com.readingroom.util.reporter;

import com.readingroom.util.EntitiesContainer;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class Reporter {

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] reportScreenshot(String name) {
        try {
            return ((TakesScreenshot) EntitiesContainer.getBrowserDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new byte[]{0};
        }
    }

    @Attachment(value = "Report screenshot", type = "image/png")
    public static byte[] reportScreenshot() {
        try {
            return ((TakesScreenshot) EntitiesContainer.getBrowserDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return new byte[]{0};
        }
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] reportErrorScreenshot(String name) {
        try {
            return ((TakesScreenshot) EntitiesContainer.getBrowserDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (RuntimeException e) {
            return new byte[]{0};
        }
    }

    @Step("{0}")
    public static void report(String text) {}
}