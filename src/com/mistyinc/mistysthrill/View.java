package com.mistyinc.mistysthrill;

import com.mistyinc.mistysthrill.controllers.BookmarkController;
import com.mistyinc.mistysthrill.constants.KidFriendlyStatus;
import com.mistyinc.mistysthrill.constants.UserType;
import com.mistyinc.mistysthrill.entities.Bookmark;
import com.mistyinc.mistysthrill.entities.User;
import com.mistyinc.mistysthrill.partner.Shareable;

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

                if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
//                                        Mark as kid-friendly

                    if (bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
                        String kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                        if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
                            BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
                        }
                    }

//                    Sharing ...
                    if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED) && bookmark instanceof Shareable) {
                        boolean canBeShared = getShareableDecision();
                        if (canBeShared) {
                            BookmarkController.getInstance().share(user, bookmark);
                        }
                    }
                }

            }
        }

    }

    // TODO: Below method should simulate user input via console input
    private static String getKidFriendlyStatusDecision(Bookmark bookmark) {
        return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED : (Math.random() >= 0.4 && Math.random() < 0.8 ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN);
    }

    private static boolean getShareableDecision() {
        return Math.random() < 0.5 ? true : false;
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
