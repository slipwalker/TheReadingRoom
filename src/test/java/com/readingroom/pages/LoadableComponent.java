package com.readingroom.pages;

public interface LoadableComponent<T> {
    public void load();
    public void unload();
    public void isLoaded() throws java.lang.Error;
    public T get();
}