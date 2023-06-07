package com.kenzie.cookies;

import com.kenzie.cookies.cookie.ChocolateChipCookie;
import com.kenzie.cookies.cookie.CookieBox;
import com.kenzie.cookies.cookie.CookieIngredient;
import com.kenzie.cookies.exception.CookieProductionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Automates the Whole Foods cookie production line.
 */
public class ProductionLine {
    private Logger logger = LogManager.getLogger(ProductionLine.class);
    private Mixer mixer;
    private Oven oven;
    private CookiePackager cookiePackager;

    public ProductionLine(Mixer mixer, Oven oven, CookiePackager cookiePackager) {
        this.mixer = mixer;
        this.oven = oven;
        this.cookiePackager = cookiePackager;
    }

    /**
     * Runs the production line to produce the given number of cookies. Each cookie should be mixed, baked, and
     * packaged into boxes. If any allergen is detected, halt production, the machinery needs to be decontaminated.
     * @param numberOfCookiesToMake Number of cookies to attempt to make and package.
     * @return The boxes of cookies that are ready to be sold.
     * @throws CookieProductionException if an issue occurs and the production line needs to be halted
     */
    public List<CookieBox> createBatch(int numberOfCookiesToMake) {
        for (int i = 0; i < numberOfCookiesToMake; i++) {
            List<CookieIngredient> ingredientTypes = mixer.mix();
            ChocolateChipCookie cookie = oven.bake(ingredientTypes);
            // TODO: package cookie
        }
        return cookiePackager.getCookieBoxes();
    }
}