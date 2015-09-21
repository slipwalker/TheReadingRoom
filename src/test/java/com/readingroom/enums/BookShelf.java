package com.readingroom.enums;

public enum BookShelf {
    WANT_TO_READ("Want To Read"),
    READING_NOW("Reading Now"),
    HAVE_READ("Have Read");

    private String name;

    private BookShelf(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public BookShelf findByName(String name) {
        for (BookShelf shelf: values())
            if (shelf.toString().equals(name))
                return shelf;
        throw new IllegalArgumentException("Unknown shelf name: " + name);
    }
}