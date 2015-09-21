package com.readingroom.pages;

import com.readingroom.webdriver.ExtendedWebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public abstract class Page<T> implements LoadableComponent<T> {
    protected ExtendedWebDriver web;

    public Page(ExtendedWebDriver web) {
        this.web = web;
        get();
        init();
    }

    public abstract void init();

    public T get() {
        try {
            isLoaded();
            HtmlElementLoader.populatePageObject((T) this, web);
            return (T) this;
        } catch (Throwable e) {
            load();
        }
        isLoaded();
        HtmlElementLoader.populatePageObject((T) this, web);
        return (T) this;
    }

    public ExtendedWebDriver getDriver() {
        return web;
    }
}