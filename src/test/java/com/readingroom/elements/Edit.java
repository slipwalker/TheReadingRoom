package com.readingroom.elements;

import com.readingroom.util.EntitiesContainer;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class Edit extends TextInput {
    public Edit(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public void type(String keys) {
        clear();
        sendKeys(keys);
    }

    public void type(String keys, int sleep) {
        clear();
        if (keys.endsWith(Keys.ENTER.toString())) {
            sendKeys(keys.substring(0, keys.length() - 1));
            EntitiesContainer.getExtendedDriver().sleep(sleep);
            sendKeys(Keys.ENTER);
        } else if (keys.endsWith(Keys.RETURN.toString())) {
            sendKeys(keys.substring(0, keys.length() - 1));
            EntitiesContainer.getExtendedDriver().sleep(sleep);
            sendKeys(Keys.RETURN);
        } else
            sendKeys(keys);
    }

    public void typeWithInterval(String keys, int interval) {
        clear();
        for (Character c : keys.toCharArray()) {
            EntitiesContainer.getExtendedDriver().sleep(interval);
            sendKeys(c.toString());
        }
    }

    public void setValue(String value) {
        EntitiesContainer.getExtendedDriver().setValueThroughJavascript(value, getWrappedElement());
    }
}