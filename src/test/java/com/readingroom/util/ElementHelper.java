package com.readingroom.util;

import org.openqa.selenium.WebElement;

public class ElementHelper {

    public static WebElement moveToElement(WebElement element) {
        EntitiesContainer.getExecutor().executeScript("arguments[0].scrollIntoView();", element);
        return element;
    }

    public static void setValue(WebElement element, String value) {
        EntitiesContainer.getExecutor().executeScript("$(arguments[0]).val(arguments[1]).change();", element, value);
    }

    public static void submitForm(WebElement element) {
        EntitiesContainer.getExecutor().executeScript("arguments[0].submit(true);", element);
    }
}