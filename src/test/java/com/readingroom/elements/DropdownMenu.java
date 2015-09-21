package com.readingroom.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;
import java.util.List;

public class DropdownMenu extends TypifiedElement {
    public DropdownMenu(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public List<WebElement> getMenuOptions() {
        return getWrappedElement().findElements(By.tagName("li"));
    }

    public void selectByVisibleText(String text) {
        if (!getSelectedOption().equals(text)) {
            getWrappedElement().click();
            getWrappedElement().findElement(By.xpath(".//li[normalize-space(.)='" + text + "']")).click();
        }
    }

    public String getSelectedOption() {
        return getWrappedElement().findElement(By.cssSelector("button:nth-child(1)")).getText();
    }
}