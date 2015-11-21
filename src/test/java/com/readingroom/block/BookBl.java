package com.readingroom.block;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

@FindBy(className = "book-block")
public class BookBl extends HtmlElement {

    private final static String ROOT_NODE = ".book-block";

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(outputType);
    }

    @FindBy(css = ROOT_NODE + " img")
    private Image image;

    @FindBy(css = ROOT_NODE + " .book-title")
    private TextBlock title;

    @FindBy(css = ROOT_NODE + " .book-author")
    private TextBlock author;

    public String getTitle() {
        return title.getText();
    }

    public String getAuthor() {
        return author.getText();
    }
}