package com.mistyinc.mistysthrill.entities;

import com.mistyinc.mistysthrill.constants.MovieGenre;
import com.mistyinc.mistysthrill.managers.BookmarkManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    @Test
    public void isKidFriendlyEligible() {
//        Test 1
        Movie movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941, new String[]{"Orson Welles", "Joseph Cotten"}, new String[]{"Orson Welles"}, MovieGenre.HORROR, 8.5);
        boolean isKidFriendlyEligibleV = movie.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible is false - for horror genre", isKidFriendlyEligibleV);
//        Test 2
        movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941, new String[]{"Orson Welles", "Joseph Cotten"}, new String[]{"Orson Welles"}, MovieGenre.THRILLERS, 8.5);
        isKidFriendlyEligibleV = movie.isKidFriendlyEligible();

        assertFalse("isKidFriendlyEligible is false - for thriller genre", isKidFriendlyEligibleV);

    }
}