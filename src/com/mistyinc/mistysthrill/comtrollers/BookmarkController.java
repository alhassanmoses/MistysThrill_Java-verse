package com.mistyinc.mistysthrill.comtrollers;

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
}
