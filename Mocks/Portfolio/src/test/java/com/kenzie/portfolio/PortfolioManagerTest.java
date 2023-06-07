package com.kenzie.portfolio;

import com.kenzie.portfolio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PortfolioManagerTest {
    private Stock amznStock = new Stock("amzn", "Amazon");
    private BigDecimal currentAmazonStockPrice = BigDecimal.valueOf(1_000L);
    private int quantityInPortfolio = 3;
    private Map<Stock, Integer> portfolioStocks;

    private Stock nonExistentStock = new Stock("id", "name");

    private Portfolio portfolio;
    private StockExchangeClient client;

    private PortfolioManager portfolioManager;

    @BeforeEach
    void setUp() {
        portfolioStocks = new HashMap<>();
        portfolioStocks.put(amznStock, quantityInPortfolio);

        portfolio = new Portfolio(portfolioStocks);
        client = new StockExchangeClient(new StockExchange());

        portfolioManager = new PortfolioManager(portfolio, client);
    }

    @Test
    void getMarketValue_portfolioWithStocks_returnsValueOfStocks() {
        // GIVEN
        BigDecimal expectedValue = currentAmazonStockPrice.multiply(BigDecimal.valueOf(quantityInPortfolio));

        // WHEN
        BigDecimal value = portfolioManager.getMarketValue();

        // THEN
        assertEquals(expectedValue, value);
    }

    @Test
    void buy_existingStock_returnsCostOfBuyingStock() {
        // GIVEN
        int quantityToBuy = 5;
        BigDecimal expectedCost = currentAmazonStockPrice.multiply(BigDecimal.valueOf(quantityToBuy));

        // WHEN
        BigDecimal cost = portfolioManager.buy(amznStock, quantityToBuy);

        // THEN
        assertEquals(expectedCost, cost);
    }

    @Test
    void buy_nonExistingStock_returnsNull() {
        // GIVEN
        int quantityToBuy = 5;

        // WHEN
        BigDecimal cost = portfolioManager.buy(nonExistentStock, quantityToBuy);

        // THEN
        assertNull(cost);
    }

    @Test
    void sell_enoughStocksToSell_returnValueSoldFor() {
        // GIVEN
        int quantityToSell = quantityInPortfolio - 1;
        BigDecimal expectedValue = currentAmazonStockPrice.multiply(BigDecimal.valueOf(quantityToSell));

        // WHEN
        BigDecimal value = portfolioManager.sell(amznStock, quantityToSell);

        // THEN
        assertEquals(expectedValue, value);
    }

    @Test
    void sell_notEnoughStocksToSell_returnZeroValue() {
        // GIVEN
        int quantityToSell = quantityInPortfolio + 1;

        // WHEN
        BigDecimal value = portfolioManager.sell(amznStock, quantityToSell);

        // THEN
        assertEquals(BigDecimal.ZERO, value);
    }
}
