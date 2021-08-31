package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.UserBookmark;

public class BookmarkDao {

    public Bookmark[][] getBookmarks(){
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
    }
}
