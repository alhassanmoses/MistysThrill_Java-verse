package com.mistyinc.mistysthrill.managers;

import com.mistyinc.mistysthrill.constants.BookGenre;
import com.mistyinc.mistysthrill.constants.KidFriendlyStatus;
import com.mistyinc.mistysthrill.constants.MovieGenre;
import com.mistyinc.mistysthrill.dao.BookmarkDao;
import com.mistyinc.mistysthrill.entities.*;
import com.mistyinc.mistysthrill.util.HttpConnect;
import com.mistyinc.mistysthrill.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public class BookmarkManager {
    private static BookmarkManager instance = new BookmarkManager();
    private static BookmarkDao dao = new BookmarkDao();

    private BookmarkManager() {
    }

    public static BookmarkManager getInstance() {
        return instance;
    }

    public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast, String[] directors, MovieGenre genre, double imdbRating) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setProfileUrl(profileUrl);
        movie.setReleaseYear(releaseYear);
        movie.setCast(cast);
        movie.setDirectors(directors);
        movie.setGenre(genre);
        movie.setImdbRating(imdbRating);

        return movie;
    }

    public Book createBook(long id, String title, int publicationYear, String publisher, String[] authors, BookGenre genre, double amazonRating) {
        Book book = new Book();

        book.setId(id);
        book.setTitle(title);
//        book.setProfileUrl(profileUrl);
        book.setPublicationYear(publicationYear);
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setGenre(genre);
        book.setAmazonRating(amazonRating);

        return book;
    }

    public WebLink createWebLink(long id, String title, String url, String host) {
        WebLink webLInk = new WebLink();

        webLInk.setId(id);
        webLInk.setTitle(title);
        webLInk.setUrl(url);
        webLInk.setHost(host);

        return webLInk;
    }

    public static List<List<Bookmark>> getBookmarks() {
        return dao.getBookmarks();
    }

    public void saveUserBookmark(User user, Bookmark bookmark) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.setUser(user);
        userBookmark.setBookmark(bookmark);

        dao.saveUserBookmark(userBookmark);
    }

    public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
        bookmark.setKidFriendlyStatus(kidFriendlyStatus);
        bookmark.setKidFriendlyMarkedBy(user);
        dao.updateKidFriendlyStatus(bookmark);
        System.out.println("Kid-friendly status: " + kidFriendlyStatus + ", Marked by: " + user.getEmail() + ", " + bookmark);
    }

    public void share(User user, Bookmark bookmark) {
        bookmark.setSharedBy(user);

        System.out.println("Data to be shared: ");

        if (bookmark instanceof Book) {
            System.out.println(((Book) bookmark).getItemData());
        } else if (bookmark instanceof WebLink) {
            System.out.println(((WebLink) bookmark).getItemData());
        }

        dao.sharedByInfo(bookmark);
    }
}
