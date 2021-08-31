package com.mistyinc.mistysthrill.entities;

import com.mistyinc.mistysthrill.constants.BookGenre;
import com.mistyinc.mistysthrill.managers.BookmarkManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void isKidFriendlyEligible() {
//        Test 1
        Book book = BookmarkManager.getInstance().createBook(4000, "Walden", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.PHILOSOPHY, 4.3);
        boolean isKidFriendlyEligibleV = book.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible is false - for Philosophy Genre", isKidFriendlyEligibleV);

//        Test 2
        book = BookmarkManager.getInstance().createBook(4000, "Walden", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.SELF_HELP, 4.3);
        isKidFriendlyEligibleV = book.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible is false - for Self help Genre", isKidFriendlyEligibleV);

    }

}