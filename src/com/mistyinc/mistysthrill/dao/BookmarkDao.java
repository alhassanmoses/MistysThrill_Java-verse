package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//A stand-in for an actual SQL server :)
public class BookmarkDao {

    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    private void saveUserWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "inset into User_Book (user_id, book_id) values (" + userBookmark.getUser().getId() + ", " + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "inset into User_Movie (user_id, movie_id) values (" + userBookmark.getUser().getId() + ", " + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "inset into User_WebLink (user_id, weblink_id) values (" + userBookmark.getUser().getId() + ", " + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mistys_thrill?useSSL=false", "root", "root"); Statement stmt = conn.createStatement();) {
            if (userBookmark.getBookmark() instanceof Book) {
                saveUserBook(userBookmark, stmt);
            } else if (userBookmark.getBookmark() instanceof Movie) {
                saveUserMovie(userBookmark, stmt);
            } else {
                saveUserWebLink(userBookmark, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        DataStore.add(userBookmark);
    }


    // collecting all bookmarks from the data store, retrieving and casting
    // selected ones down to weblinks and adding them to result
    public List<WebLink> getAllWebLinks() {
        List<WebLink> result = new ArrayList<>();
        List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
        List<Bookmark> allWebLinks = bookmarks.get(0);

        allWebLinks.forEach(wl -> result.add((WebLink) wl));
        return result;
    }

    //filtering out only new weblinks for processing(downloading)
    public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
        List<WebLink> result = new ArrayList<>();

        result = getAllWebLinks().stream().filter(webLink -> webLink.getDownloadStatus().equals(downloadStatus)).collect(Collectors.toList());

        return result;
    }

    public void updateKidFriendlyStatus(Bookmark bookmark) {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof Movie) {
            tableToUpdate = "Movie";
        } else if (bookmark instanceof WebLink) {
            tableToUpdate = "WebLink";
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mistys_thrill?useSSL=false", "root", "root"); Statement stmt = conn.createStatement();) {
            String query = "update " + tableToUpdate + " set kid_friendly_status = " + kidFriendlyStatus + ", kid_friendly_marked_by " + userId + " where id = " + bookmark.getId();
            System.out.println("query (updateKidFriendlyStatus): " + query);
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sharedByInfo(Bookmark bookmark) {
        long userId = bookmark.getSharedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof WebLink)
            tableToUpdate = "WebLink";


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mistys_thrill?useSSL=false", "root", "root"); Statement stmt = conn.createStatement();) {
            String query = "update " + tableToUpdate + " set shared_by = " + userId + " where id = " + bookmark.getId();
            System.out.println("query (updateKidFriendlyStatus): " + query);
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
