package com.mistyinc.mistysthrill;

import com.mistyinc.mistysthrill.comtrollers.BookmarkController;
import com.mistyinc.mistysthrill.constants.KidFriendlyStatus;
import com.mistyinc.mistysthrill.constants.UserType;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.User;

public class View {

    public static void browse(User user, Bookmark[][] bookmarks) {
        System.out.println("\n" + user.getEmail() + " is browsing items ...");

        int bookmarkCount = 0;

        for (Bookmark[] bookmarkList :
                bookmarks) {
            for (Bookmark bookmark :
                    bookmarkList) {
                if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
                    boolean isBookmark = getBookmarkDecision(bookmark);
                    if (isBookmark) {
                        bookmarkCount++;
                        BookmarkController.getInstance().saveUserBookmark(user, bookmark);
                        System.out.println("New item bookmarked ... \n" + bookmark);
                    }
                }
//                    Mark as kid-friendly
                if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
                    if (bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
                        String  kidFriendlystatus = getKidFriendlyStatusDecision(bookmark);
                        if (!kidFriendlystatus.equals(KidFriendlyStatus.UNKNOWN)){
                            bookmark.setKidFriendlyStatus(kidFriendlystatus);
                            System.out.println("Kid-friendly status: " + kidFriendlystatus + ", " + bookmark);
                        }
                    }
                }

            }
        }

    }

    private static String getKidFriendlyStatusDecision(Bookmark bookmark) {
        return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED : (Math.random() >= 0.4 && Math.random() < 0.8 ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN);
    }


    private static boolean getBookmarkDecision(Bookmark bookmark) {
        return Math.random() < 0.5 ? true : false;
    }


    /*public static void bookmark(User user, Bookmark[][] bookmarks){
        System.out.println("\n" + user.getEmail() + " is bookmarking");
        for (int i = 0; i < DataStore.USER_BOOKMARK_LIMIT; i++) {
           int typeoffset = (int) (Math.random() * DataStore.BOOKMARK_TYPES_COUNT);
           int bookmarkoffset = (int) (Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);

           Bookmark bookmark = bookmarks[typeoffset][bookmarkoffset];

            BookmarkController.getInstance().saveUserBookmark(user,bookmark);

            System.out.println(bookmark);
        }
    }*/
}
