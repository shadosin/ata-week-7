package com.kenzie.portfolio;

import com.kenzie.portfolio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StockExchangeClientTest {
    @Mock
    private StockExchange stockExchange;

    private String existingStockSymbol = "wfm";
    private Stock existingStock = new Stock(existingStockSymbol, "Whole Foods");
    private BigDecimal currentExistingStockPrice = BigDecimal.valueOf(100L);

    private String nonExistentStockSymbol = "nonexistent";
    private Stock nonExistentStock = new Stock(nonExistentStockSymbol, "name");

    @InjectMocks
    private StockExchangeClient client;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    void getPrice_existingStock_returnsPrice() throws NonExistentStockException {
        // GIVEN
        StockPriceResponse response = StockPriceResponse.builder()
            .withSymbol(existingStockSymbol)
            .withPrice(currentExistingStockPrice)
            .build();
        when(stockExchange.getMarketPrice(existingStockSymbol)).thenReturn(response);

        // WHEN
        BigDecimal price = client.getPrice(existingStock);

        // THEN
        assertEquals(currentExistingStockPrice, price);
    }

    @Test
    void getPrice_nonExistentStock_returnsNull() {
        // GIVEN

        // WHEN
        BigDecimal price = client.getPrice(nonExistentStock);

        // THEN
        assertNull(price);
    }

    @Test
    void submitBuy_nonExistentStock_returnsNull() throws NonExistentStockException {
        // GIVEN
        int quantity = 3;
        when(stockExchange.bid(any(BuyStockRequest.class))).thenThrow(NonExistentStockException.class);

        // WHEN
        BigDecimal price = client.submitBuy(nonExistentStock, quantity);

        // THEN
        assertNull(price);
    }

    @Test
    void submitSell_existingStock_returnsPrice() throws NonExistentStockException {
        // GIVEN
        int quantity = 3;
        SellStockResponse response = SellStockResponse.builder()
            .withSymbol(existingStockSymbol)
            .withQuantity(quantity)
            .withPrice(currentExistingStockPrice.multiply(BigDecimal.valueOf(quantity)))
            .build();
        when(stockExchange.offer(any(SellStockRequest.class))).thenReturn(response);

        // WHEN
        BigDecimal price = client.submitSell(existingStock, quantity);

        // THEN
        assertEquals(currentExistingStockPrice.multiply(BigDecimal.valueOf(quantity)), price);
    }

    @Test
    void submitSell_nonExistentStock_returnsNull() throws NonExistentStockException {
        // GIVEN
        int quantity = 3;
        when(stockExchange.offer(any(SellStockRequest.class))).thenThrow(NonExistentStockException.class);

        // WHEN
        BigDecimal price = client.submitSell(nonExistentStock, quantity);

        // THEN
        assertNull(price);
    }
}
