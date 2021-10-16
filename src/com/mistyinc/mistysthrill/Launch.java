package com.mistyinc.mistysthrill;

import com.mistyinc.mistysthrill.bgjobs.WebpageDownloaderTask;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.User;
import com.mistyinc.mistysthrill.managers.BookmarkManager;
import com.mistyinc.mistysthrill.managers.UserManager;

import java.util.List;

public class Launch {

    private static List<User> users;
    private static List<List<Bookmark>> bookmarks;


    private static void loadData() {
        System.out.println("Loading data ...");
        DataStore.loadData();
        users = UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();

//        System.out.println("Printing Data ...");
//        printUserData();
//        printBookmarkData();

    }

    private static void printUserData() {
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void printBookmarkData() {
        for (List<Bookmark> bookmarkList : bookmarks) {
            for (Bookmark bookmark : bookmarkList) {
                System.out.println(bookmark);
            }

        }
    }

    private static void start() {
//        System.out.println("\nBookmarking ...");
        for (User user : users) {
            View.browse(user, bookmarks);
        }
    }

    private static void runDownloaderJob(){
        WebpageDownloaderTask task = new WebpageDownloaderTask(true);

        (new Thread(task)).start();
    }

    public static void main(String[] args) {
        loadData();
        //simulating a user browsing the site and making random bookmarks
        start();

        //Beginning the background weblink downloader job
        runDownloaderJob();
    }


}
