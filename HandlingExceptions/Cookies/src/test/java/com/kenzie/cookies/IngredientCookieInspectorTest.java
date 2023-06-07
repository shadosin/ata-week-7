package com.kenzie.cookies;

import com.kenzie.cookies.cookie.ChocolateChipCookie;
import com.kenzie.cookies.cookie.CookieIngredient;
import com.kenzie.cookies.cookie.Size;
import com.kenzie.cookies.exception.AllergenContaminantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IngredientCookieInspectorTest {
    private IngredientCookieInspector inspector;

    @BeforeEach
    public void setup() {
        inspector = new IngredientCookieInspector();
    }

    @Test
    public void inspect_cookieContainsPeanuts_throwsAllergenException() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM,
                10,
                Arrays.asList(CookieIngredient.PEANUT));

        // WHEN + THEN
        assertThrows(AllergenContaminantException.class, () -> inspector.inspect(cookie));
    }
}