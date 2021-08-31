package com.mistyinc.mistysthrill.entities;

import com.mistyinc.mistysthrill.managers.BookmarkManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebLinkTest {

    @Test
    public void isKidFriendlyEligible() {
//        Test 1: porn in url -- false
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html", "http://www.javaworld.com");
        boolean isKidFriendlyEligibleV = webLink.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible() is false if the word porn exist in the bookmark's url", isKidFriendlyEligibleV);

//        Test 2: porn in title -- false

        webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming porn , Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligibleV = webLink.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible() is false if the word porn exist in the bookmark's title", isKidFriendlyEligibleV);

//        Test 3: adult in host -- false

        webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.adult.com");
        isKidFriendlyEligibleV = webLink.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible() is false if the word adult exist in the bookmark's host url", isKidFriendlyEligibleV);

//        Test 4: adult in url, but not in host part -- true

        webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligibleV = webLink.isKidFriendlyEligible();

        assertTrue("isKidFriendlyEligible() is true if the word adult exist in the bookmark's url but not in its title", isKidFriendlyEligibleV);

//        Test 5: adult in title only --true

        webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming adult, Part 2", "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html", "http://www.javaworld.com");
        isKidFriendlyEligibleV = webLink.isKidFriendlyEligible();

        assertTrue("isKidFriendlyEligible() is false if the word adult exist in the bookmark's host title", isKidFriendlyEligibleV);

    }
}