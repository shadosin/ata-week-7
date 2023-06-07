package com.kenzie.portfolio;

import java.util.HashMap;
import java.util.Map;

/**
 * A portfolio, or collection, of stocks a customer owns.
 */
public class Portfolio {
    private Map<Stock, Integer> stocks;

    /**
     * Create a portfolio with a collection of stocks and the amount owned of each stock.
     * @param stocks Stocks present in portfolio
     */
    public Portfolio(Map<Stock, Integer> stocks) {
        this.stocks = new HashMap<>(stocks);
    }

    /**
     * Add a quantity of stocks to the portfolio.
     * @param stock Which stock to add
     * @param quantity How many of that stock to add
     */
    public void addStocks(Stock stock, int quantity) {
        int existingQuantity = stocks.getOrDefault(stock, 0);
        stocks.put(stock, existingQuantity + quantity);
    }

    /**
     * Remove a quantity of stocks from the portfolio.
     * @param stock Which stock to remove
     * @param quantity How many of the specified stocks to remove
     * @throws com.kenzie.resources.InsufficientStockException if there aren't enough of the stocks present in the portfolio to remove.
     */
    public void removeStocks(Stock stock, int quantity) throws InsufficientStockException {
        int existingQuantity = stocks.getOrDefault(stock, 0);
        if (quantity > existingQuantity) {
            throw new InsufficientStockException(
                String.format("The portfolio contained %d %s stocks. %d could not be removed.",
                    existingQuantity, stock.getName(), quantity));
        }

        if (existingQuantity - quantity == 0) {
            stocks.remove(stock);
        } else {
            stocks.put(stock, existingQuantity - quantity);
        }
    }

    /**
     * A copy of the stocks and quantity of each stock that make up this portfolio.
     * @return A map of Stock to the quantity of that stock contained in this portfolio.
     */
    public Map<Stock, Integer> getStocks() {
        Map<Stock, Integer> mapCopy = new HashMap<>();
        for (Map.Entry<Stock, Integer> stockQuantity : stocks.entrySet()) {
            Stock stockCopy = new Stock(stockQuantity.getKey().getSymbol(), stockQuantity.getKey().getName());
            mapCopy.put(stockCopy, stockQuantity.getValue());
        }
        return mapCopy;
    }
}
