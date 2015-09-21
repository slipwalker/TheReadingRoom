package com.readingroom.util.testdata;

import com.google.common.reflect.TypeToken;
import com.readingroom.models.Book;
import com.readingroom.models.User;
import com.readingroom.util.common.JsonConverter;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class TestDataContainer {

    private static final String DATA_RESOURCE_ROOT_FOLDER = "src/test/resources/data/";

    private final static TestDataContainer TEST_DATA_CONTAINER = new TestDataContainer();

    private TestDataContainer() {
    }

    public static TestDataContainer getInstance() {
        return TEST_DATA_CONTAINER;
    }

    public static User getUserByType(String type) {
        return readUsersJsonFile().get(type);
    }

    public static List<User> getUsers() {
        return readUsersList().getList();
    }

    public static Book getBookByName(String name) {
        return readBooksJsonFile().get(name);
    }

    public static Book getDefaultBook() {
        return readBooksJsonFile().get("Inferno");
    }

    private static Map<String, User> readUsersJsonFile() {
        Type resultType = new TypeToken<Map<String, User>>(){
        }.getType();
        return JsonConverter.readJsonFile(getFileByName("users.json"), resultType);
    }

    private static ListContainer<User> readUsersList() {
        Type resultType = new  TypeToken<ListContainer<User>>(){
        }.getType();
        return JsonConverter.readJsonFile(getFileByName("usersList.json"), resultType);
    }

    private static Map<String, Book> readBooksJsonFile() {
        Type resultType = new TypeToken<Map<String, Book>>(){
        }.getType();
        return JsonConverter.readJsonFile(getFileByName("books.json"), resultType);
    }

    private static File getFileByName(String fileName) {
        return new File(DATA_RESOURCE_ROOT_FOLDER + fileName);
    }
}