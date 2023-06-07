package com.kenzie.cookies;

import com.kenzie.cookies.cookie.ChocolateChipCookie;
import com.kenzie.cookies.cookie.CookieIngredient;
import com.kenzie.cookies.cookie.Size;
import com.kenzie.cookies.exception.CookieDependencyException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OvenTest {
    private Oven oven;

    @BeforeEach
    public void setUp() {
        oven = new Oven(new Random(10));
    }

    @Test
    public void bake_ovenOff_throwsCookieDependencyException() {
        // GIVEN
        List<CookieIngredient> ingredients = Arrays.asList(CookieIngredient.CHOCOLATE);
        oven.turnOff();

        // WHEN
        assertThrows(CookieDependencyException.class, () -> oven.bake(ingredients));
    }

    @Test
    public void bake_bakeIngredients_returnsRandomCookie() {
        // GIVEN
        List<CookieIngredient> ingredients = Arrays.asList(CookieIngredient.CHOCOLATE);

        // WHEN
        ChocolateChipCookie cookie = oven.bake(ingredients);

        // THEN
        ChocolateChipCookie expectedCookie = new ChocolateChipCookie(Size.SMALL, 5,ingredients);
        assertEquals(expectedCookie, cookie);
    }

    @Test
    public void bake_bakeIngredients_returnsRandomCookieAgain() {
        // GIVEN
        List<CookieIngredient> ingredients = Arrays.asList(CookieIngredient.BAKING_SODA,
                CookieIngredient.GRANULATED_SUGAR,
                CookieIngredient.EGG);

        // WHEN
        oven.bake(ingredients);
        ChocolateChipCookie cookie = oven.bake(ingredients);

        // THEN
        ChocolateChipCookie expectedCookie = new ChocolateChipCookie(Size.SMALL, 15, ingredients);
        assertEquals(expectedCookie, cookie);
    }
}