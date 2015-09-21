package com.readingroom.tests.bookshelf;

import com.readingroom.enums.BookShelf;
import com.readingroom.models.Book;
import com.readingroom.pages.BookOverviewPage;
import com.readingroom.pages.BookshelfPage;
import com.readingroom.pages.UserOverviewPage;
import com.readingroom.tests.DataProviderFactory;
import com.readingroom.tests.TestBase;
import com.readingroom.util.EntitiesContainer;
import com.readingroom.util.common.Log;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Features({"Bookshelf Interaction"})
public class BookshelfTest extends TestBase {

    private UserOverviewPage userOverviewPage;

    private BookshelfPage bookShelfPage;

    private UserOverviewPage openUserOverviewPage() {
        return getPageManager().getPageNavigator().getUserOverviewPage();
    }

    private BookshelfPage openWantToReadBookshelf(String userName) {
        return getPageManager().getPageNavigator().getWantToReadBookShelfPage(userName);
    }

    private BookshelfPage openReadingNowBookshelf(String userName) {
        return getPageManager().getPageNavigator().getReadingNowBookShelfPage(userName);
    }

    private BookshelfPage openHaveReadBookshelf(String userName) {
        return getPageManager().getPageNavigator().getHaveReadBookShelfPage(userName);
    }

    @Test(dataProvider = "BookShelves", dataProviderClass = DataProviderFactory.class)
    @Stories("Alert about wrong user credentials")
    @Description("Verify that proper alert message is shown after selecting a bookshelf")
    public void CheckAlertMessageAfterSelectingBookshelf(Book book, BookShelf bookShelf) {
        Log.INFO("Book: " + book.getName() + ", bookshelf: " + bookShelf.toString(), true);

        BookOverviewPage bookOverviewPage = userOverviewPage
                .openBookOverviewPage(book.getName())
                .addToShelf(bookShelf.toString());

        assertThat("Check that proper alert is shown after selecting bookshelf: " + bookShelf.toString(),
                bookOverviewPage.getAlertMessage(), equalTo("Book added successfully"));
    }

    @Test(dataProvider = "BookShelves", dataProviderClass = DataProviderFactory.class)
    @Stories("Bookshelf label changing on the book overview page")
    @Description("Verify that proper bookshelf label is shown after selecting")
    public void CheckSelectingDifferentBookshelf(Book book, BookShelf bookShelf) {
        Log.INFO("Book: " + book.getName() + ", bookshelf: " + bookShelf.toString(), true);

        BookOverviewPage bookOverviewPage = userOverviewPage
                .openBookOverviewPage(book.getName())
                .addToShelf(bookShelf.toString());

        assertThat("Check that bookshelf is selected: ", bookOverviewPage.getSelectedBookShelf(), equalTo(bookShelf.toString()));

    }

    @Test(dataProvider = "BookShelves", dataProviderClass = DataProviderFactory.class)
    @Stories("Book is passed to proper bookshelf")
    @Description("Verify that book is shown on the proper bookshelf")
    public void CheckThatBookIsShownOnBookshelf(Book book, BookShelf bookShelf) {
        Log.INFO("Book: " + book.getName() + ", bookshelf: " + bookShelf.toString(), true);

        userOverviewPage
                .openBookOverviewPage(book.getName())
                .addToShelf(bookShelf.toString());

        switch (bookShelf) {
            case WANT_TO_READ: bookShelfPage = openWantToReadBookshelf(EntitiesContainer.getCurrentUser().getName()); break;
            case HAVE_READ: bookShelfPage = openHaveReadBookshelf(EntitiesContainer.getCurrentUser().getName()); break;
            case READING_NOW: bookShelfPage = openReadingNowBookshelf(EntitiesContainer.getCurrentUser().getName()); break;
            default:
                throw new IllegalArgumentException("Unknown bookshelf: " + bookShelf.toString());
        }

        assertThat("Check that book is shown on the proper bookshelf: ", bookShelfPage.getBookCoversSize(), greaterThan(0));
    }

    @BeforeMethod
    public void reOpenUserOverviewPage() {
        userOverviewPage = openUserOverviewPage();
    }
}