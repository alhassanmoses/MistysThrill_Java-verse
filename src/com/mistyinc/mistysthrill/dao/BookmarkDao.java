package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.UserBookmark;

import java.util.List;

public class BookmarkDao {

    public List<List<Bookmark>> getBookmarks(){
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
    }
}
