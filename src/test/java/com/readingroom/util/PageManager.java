package com.readingroom.util;

import com.readingroom.factorycomponents.PageCreator;
import com.readingroom.factorycomponents.PageNavigator;
import java.net.URL;

public class PageManager {

    private URL siteUrl;
    private PageNavigator pageNavigator;
    private PageCreator pageCreator;

    public PageManager(URL siteUrl) {
        this.siteUrl = siteUrl;
    }

    public PageNavigator getPageNavigator() {
        if (pageNavigator == null) {
            pageNavigator = new PageNavigator(EntitiesContainer.getExtendedDriver(), this.siteUrl);
        }
        return pageNavigator;
    }

    public PageCreator getPageCreator() {
        if (pageCreator == null) {
            pageCreator = new PageCreator(EntitiesContainer.getExtendedDriver());
        }
        return pageCreator;
    }
}