package com.kenzie.cookies;

import com.kenzie.cookies.cookie.ChocolateChipCookie;
import com.kenzie.cookies.cookie.CookieBox;
import com.kenzie.cookies.cookie.CookieIngredient;
import com.kenzie.cookies.cookie.Size;
import com.kenzie.cookies.exception.AllergenContaminantException;
import com.kenzie.cookies.exception.CookieProductionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductionLineTest {

    private CookiePackager cookiePackager;
    private ProductionLine productionLine;

    @BeforeEach
    public void setUp() {
        cookiePackager =  mock(CookiePackager.class);
        productionLine = new ProductionLine(new Mixer(new Random(10)),
                new Oven(new Random(10)),
                cookiePackager);
    }

    @Test
    public void run_allergenDetected_throwsCookieProductionException() throws Exception {
        // GIVEN
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.SMALL, 5,
                Arrays.asList(CookieIngredient.FLOUR, CookieIngredient.PEANUT));
        doThrow(AllergenContaminantException.class)
                .when(cookiePackager).packageCookie(cookie);

        // WHEN & THEN
        assertThrows(CookieProductionException.class, () -> productionLine.createBatch(20),
                "Allergen detection should halt the production line and throw a CookieProductionException");
    }

    @Test
    public void run_askFor15Cookies_returnsCookieBoxes() throws Exception {
        // GIVEN
        CookieBox box1 = generateCookieBox(10);
        CookieBox box2 = generateCookieBox(5);
        List<CookieBox> cookieBoxes = Arrays.asList(box1, box2);
        when(cookiePackager.getCookieBoxes()).thenReturn(cookieBoxes);

        // WHEN
        List<CookieBox> boxes = productionLine.createBatch(15);

        // THEN
        assertNotNull(boxes, "List returned from createBatch should not be null.");
        assertEquals(2, boxes.size(), "Expected 15 cookies to return two boxes.");
        assertEquals(10, boxes.get(0).getNumberOfCookies(), "Expected 1st box to contain 10 cookies");
        assertEquals(5, boxes.get(1).getNumberOfCookies(), "Expected 2nd box to contain 5 cookies");
    }

    private CookieBox generateCookieBox(int size) {
        ChocolateChipCookie cookie = new ChocolateChipCookie(Size.MEDIUM, 8,
                Arrays.asList(CookieIngredient.FLOUR, CookieIngredient.CHOCOLATE));
        CookieBox box = new CookieBox();

        for (int i = 0; i < size; i++) {
            box.addCookie(cookie);
        }

        return box;
    }
}