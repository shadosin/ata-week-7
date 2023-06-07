package com.kenzie.cookies;

import com.kenzie.cookies.cookie.ChocolateChipCookie;
import com.kenzie.cookies.cookie.CookieIngredient;
import com.kenzie.cookies.cookie.Size;
import com.kenzie.cookies.exception.AllergenContaminantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CookiePackagerTest {
    private CookiePackager cookiePackager;

    @BeforeEach
    public void setUp() {
        cookiePackager = new CookiePackager(Arrays.asList(new IngredientCookieInspector(),
                new DeepLensCookieInspector()));
    }

    @Test
    public void addCookie_cookieTooSmall_addsToCrumble() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.SMALL, 10, new ArrayList<>());

        // WHEN
        cookiePackager.packageCookie(cookie);

        // THEN
        assertEquals(0, cookiePackager.getCookieBoxes().size(),
                "No cookie boxes should have been created.");
        assertEquals(1, cookiePackager.getCookiesForCrumble().size(),
                "Cookie should have been added once for crumble.");
        assertEquals(cookie, cookiePackager.getCookiesForCrumble().get(0),
                "Cookie for crumble is the small cookie.");
    }

    @Test
    public void addCookie_cookieTooLarge_addsToCrumble() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.LARGE, 10, new ArrayList<>());

        // WHEN
        cookiePackager.packageCookie(cookie);

        // THEN
        assertEquals(0, cookiePackager.getCookieBoxes().size(),
                "No cookie boxes should have been created.");
        assertEquals(1, cookiePackager.getCookiesForCrumble().size(),
                "Cookie should have been added once for crumble.");
        assertEquals(cookie, cookiePackager.getCookiesForCrumble().get(0),
                "Cookie for crumble is the large cookie.");
    }

    @Test
    public void addCookie_noCookiesAddedYet_addsFirstCookie() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM, 10, new ArrayList<>());

        // WHEN
        cookiePackager.packageCookie(cookie);

        // THEN
        assertEquals(0, cookiePackager.getCookiesForCrumble().size(),
                "No cookies should have been added for crumble.");
        assertEquals(1, cookiePackager.getCookieBoxes().size(),
                "One cookie box should have been assembled.");
        assertEquals(1, cookiePackager.getCookieBoxes().get(0).getNumberOfCookies(),
                "There should be one cookie in the box.");
    }

    @Test
    public void addCookie_fiveCookiesAdded_addsToExistingBox() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM, 10, new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            cookiePackager.packageCookie(cookie);
        }

        // WHEN
        cookiePackager.packageCookie(cookie);

        // THEN
        assertEquals(0, cookiePackager.getCookiesForCrumble().size(),
                "No cookies should have been added for crumble.");
        assertEquals(1, cookiePackager.getCookieBoxes().size(),
                "One cookie box should have been assembled.");
        assertEquals(6, cookiePackager.getCookieBoxes().get(0).getNumberOfCookies(),
                "There should be six cookies in the box.");
    }

    @Test
    public void addCookie_fullCookieBox_assembleNewCookieBox() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM, 10, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            cookiePackager.packageCookie(cookie);
        }

        // WHEN
        cookiePackager.packageCookie(cookie);

        // THEN
        assertEquals(0, cookiePackager.getCookiesForCrumble().size(),
                "No cookies should have been added for crumble.");
        assertEquals(2, cookiePackager.getCookieBoxes().size(),
                "Two cookie boxes should have been assembled.");
        assertEquals(10, cookiePackager.getCookieBoxes().get(0).getNumberOfCookies(),
                "There first cookie box should be full.");
        assertEquals(1, cookiePackager.getCookieBoxes().get(1).getNumberOfCookies(),
                "There second cookie box should have one cookie in it.");
    }

    @Test
    public void addCookie_cookieContainsPeanuts_propagatesException() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM,
                10,
                Arrays.asList(CookieIngredient.PEANUT));

        // WHEN + THEN
        assertThrows(AllergenContaminantException.class, () -> cookiePackager.packageCookie(cookie),
                "Expected an AllergenContaminantException to be propagated when a cookie contains" +
                        "an allergen.");
    }
}