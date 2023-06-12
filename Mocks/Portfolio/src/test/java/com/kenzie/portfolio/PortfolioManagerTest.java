package com.kenzie.portfolio;

import com.kenzie.portfolio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PortfolioManagerTest {
    private Stock amznStock = new Stock("amzn", "Amazon");
    private BigDecimal currentAmazonStockPrice = BigDecimal.valueOf(1_000L);
    private int quantityInPortfolio = 3;
    private Map<Stock, Integer> portfolioStocks;

    private Stock nonExistentStock = new Stock("id", "name");
    @Mock
    private Portfolio portfolio;
    @Mock
    private StockExchangeClient client;
    @InjectMocks
    private PortfolioManager portfolioManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


    }

    @Test
    void getMarketValue_portfolioWithStocks_returnsValueOfStocks() {
        // GIVEN
        BigDecimal expectedValue = currentAmazonStockPrice.multiply(BigDecimal.valueOf(quantityInPortfolio));

        // Create a mock instance of portfolioStocks
        Map<Stock, Integer> portfolioStocks = new HashMap<>();
        portfolioStocks.put(amznStock, quantityInPortfolio);

        // Mock the behavior of the portfolio
        when(portfolio.getStocks()).thenReturn(portfolioStocks);

        // Mock the behavior of the client
        when(client.getPrice(amznStock)).thenReturn(currentAmazonStockPrice);

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
        when(client.submitBuy(amznStock, quantityToBuy)).thenReturn(expectedCost);
        BigDecimal cost = portfolioManager.buy(amznStock, quantityToBuy);

        // THEN
        assertEquals(expectedCost, cost);
        verify(client).submitBuy(amznStock, quantityToBuy);
    }

    @Test
    void buy_nonExistingStock_returnsNull() {
        // GIVEN
        int quantityToBuy = 5;

        // WHEN
        when(client.submitBuy(nonExistentStock, quantityToBuy)).thenReturn(null);
        BigDecimal cost = portfolioManager.buy(nonExistentStock, quantityToBuy);

        // THEN
        assertNull(cost);
        verifyZeroInteractions(portfolio);
    }

    @Test
    void sell_enoughStocksToSell_returnValueSoldFor() {
        // GIVEN
        int quantityToSell = quantityInPortfolio - 1;
        BigDecimal expectedValue = currentAmazonStockPrice.multiply(BigDecimal.valueOf(quantityToSell));

        // WHEN
        when(client.submitSell(amznStock, quantityToSell)).thenReturn(expectedValue);
        BigDecimal value = portfolioManager.sell(amznStock, quantityToSell);

        // THEN
        assertEquals(expectedValue, value);
        verify(client).submitSell(amznStock, quantityToSell);
    }

    @Test
    void sell_notEnoughStocksToSell_returnZeroValue() throws InsufficientStockException {
        // GIVEN
        int quantityToSell = quantityInPortfolio + 1;
        doThrow(InsufficientStockException.class).when(portfolio).removeStocks(amznStock, quantityToSell);
        // WHEN
        BigDecimal value = portfolioManager.sell(amznStock, quantityToSell);

        // THEN
        assertEquals(BigDecimal.ZERO, value);
    }
}
