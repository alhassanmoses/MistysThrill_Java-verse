package com.mistyinc.mistysthrill.controllers;

import com.mistyinc.mistysthrill.constants.KidFriendlyStatus;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.User;
import com.mistyinc.mistysthrill.managers.BookmarkManager;

public class BookmarkController {

    private static BookmarkController instance = new BookmarkController();

    private BookmarkController() {
    }

    public static BookmarkController getInstance() {
        return instance;
    }

    public void saveUserBookmark(User user, Bookmark bookmark) {
        BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
    }

    public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
        BookmarkManager.getInstance().kidFriendlyStatus(user, kidFriendlyStatus, bookmark);
    }

    public void share(User user, Bookmark bookmark) {
        BookmarkManager.getInstance().share(user, bookmark);
    }
}
