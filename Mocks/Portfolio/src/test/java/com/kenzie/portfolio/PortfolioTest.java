package com.kenzie.portfolio;

import com.kenzie.portfolio.InsufficientStockException;
import com.kenzie.portfolio.Portfolio;
import com.kenzie.portfolio.Stock;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PortfolioTest {

    @Test
    void addStocks_addASingleStock_onlyStockPresent() {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Portfolio portfolio = new Portfolio(stocks);
        Stock stockToAdd = new Stock("id", "name");
        Integer quantityToAdd = 5;

        // WHEN
        portfolio.addStocks(stockToAdd, quantityToAdd);

        // THEN

        Map<Stock, Integer> stocksReturned = portfolio.getStocks();
        assertNotNull(stocksReturned);
        assertEquals(1, stocksReturned.size());
        assertEquals(quantityToAdd, stocksReturned.get(stockToAdd));
    }

    @Test
    void removeStocks_stockNotInPortfolio_throwsInsufficientStockException() throws InsufficientStockException {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        stocks.put(stockInPortfolio, 5);

        Portfolio portfolio = new Portfolio(stocks);

        Stock stockNotInPortfolio = new Stock("id", "name");

        // WHEN + THEN
        assertThrows(InsufficientStockException.class, () -> portfolio.removeStocks(stockNotInPortfolio, 1));
    }

    @Test
    void removeStocks_notEnoughStockInPortfolio_throwsInsufficientStockException() throws InsufficientStockException {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        int quantity = 5;
        stocks.put(stockInPortfolio, quantity);

        Portfolio portfolio = new Portfolio(stocks);

        // WHEN + THEN
        assertThrows(InsufficientStockException.class, () -> portfolio.removeStocks(stockInPortfolio, quantity + 2));
    }

    @Test
    void removeStocks_exactlyEnoughStockInPortfolio_removesFromMap() throws InsufficientStockException {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Portfolio portfolio = new Portfolio(stocks);

        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        int quantity = 5;
        portfolio.addStocks(stockInPortfolio, quantity);



        // WHEN
        portfolio.removeStocks(stockInPortfolio, quantity);

        // THEN
        assertNull(stocks.get(stockInPortfolio));
        assertNull(portfolio.getStocks().get(stockInPortfolio));
    }

    @Test
    void removeStocks_lessStockThanAvailable_remainingStockInPortfolio() throws InsufficientStockException {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Portfolio portfolio = new Portfolio(stocks);

        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        int quantity = 5;
        stocks.put(stockInPortfolio, quantity);


        int quantityToRemain = 2;
        portfolio.addStocks(stockInPortfolio, quantity);

        // WHEN
        portfolio.removeStocks(stockInPortfolio, quantity - quantityToRemain);

        // THEN
        assertEquals(quantityToRemain, portfolio.getStocks().get(stockInPortfolio));
    }

    @Test
    void getStocks_modifyingMapPassedToConstructor_doesntChangeInternalRepresentation() {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        int quantity = 5;
        stocks.put(stockInPortfolio, quantity);

        Portfolio portfolio = new Portfolio(stocks);

        // WHEN
        stocks.put(stockInPortfolio, quantity - 2);

        // THEN
        Map<Stock, Integer> expectedStocks = new HashMap<>();
        expectedStocks.put(stockInPortfolio, quantity);
        assertEquals(expectedStocks, portfolio.getStocks());
    }

    @Test
    void getStocks_portfolioIsWellEncapsulated_returnsCopyOfStocks() {
        // GIVEN
        Map<Stock, Integer> stocks = new HashMap<>();
        Stock stockInPortfolio = new Stock("amzn", "Amazon");
        int quantity = 5;
        stocks.put(stockInPortfolio, quantity);

        Portfolio portfolio = new Portfolio(stocks);

        // WHEN
        Map<Stock, Integer> copyOfStocks = portfolio.getStocks();
        copyOfStocks.put(stockInPortfolio, quantity - 2);

        // THEN
        assertEquals(stocks, portfolio.getStocks());
    }
}
