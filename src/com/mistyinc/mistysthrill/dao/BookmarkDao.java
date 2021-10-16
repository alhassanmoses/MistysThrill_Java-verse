package com.mistyinc.mistysthrill.dao;

import com.mistyinc.mistysthrill.DataStore;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.UserBookmark;
import com.mistyinc.mistysthrill.entities.WebLink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//A stand-in for an actual SQL server :)
public class BookmarkDao {

    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
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
}
