package com.kenzie.portfolio;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Manages and manipulates a portfolio using the stock exchange.
 */
public class PortfolioManager {
    private Portfolio portfolio;
    private StockExchangeClient stockExchangeClient;

    /**
     * Instantiates a new PortfolioManager with the provided portfolio and stockExchangeClient.
     * @param portfolio the portfolio that will be manged by this object
     * @param stockExchangeClient the client that will be called to retrieve stock prices, and buy/sell stocks
     */
    public PortfolioManager(Portfolio portfolio, StockExchangeClient stockExchangeClient) {
        this.portfolio = portfolio;
        this.stockExchangeClient = stockExchangeClient;
    }

    /**
     * What is the current market value of this portfolio as a whole.
     * @return USD value of the portfolio according to the current stock exchange prices.
     */
    public BigDecimal getMarketValue() {
        Map<Stock, Integer> portfolioStocks = portfolio.getStocks();
        BigDecimal marketValue = BigDecimal.ZERO;

        for (Map.Entry<Stock, Integer> stockToQuantity : portfolioStocks.entrySet()) {
            BigDecimal price = stockExchangeClient.getPrice(stockToQuantity.getKey());
            BigDecimal priceOfStock = price.multiply(BigDecimal.valueOf(stockToQuantity.getValue()));
            marketValue = marketValue.add(priceOfStock);
        }

        return marketValue;
    }

    /**
     * Buy stocks off the stock exchange and add them to the portfolio.
     * @param stock Which stock to buy
     * @param quantity How many shares of the stock to buy
     * @return USD cost of buying the stocks off the exchange
     */
    public BigDecimal buy(Stock stock, int quantity) {
        BigDecimal cost = stockExchangeClient.submitBuy(stock, quantity);

        // Cost is null if the stock doesn't exist on the market and therefore couldn't be purchased.
        if (cost != null) {
            portfolio.addStocks(stock, quantity);
        }
        return cost;
    }

    /**
     * Sell stocks from the portfolio on the stock exchange. If the portfolio does not contain the stock specified or
     * have enough shares, it will not be sold.
     * @param stock Which stock to sell
     * @param quantity How many shares of the stock to sell.
     * @return USD value acquired by selling stocks or BigDecimal.ZERO if the stock couldn't be sold.
     */
    public BigDecimal sell(Stock stock, int quantity) {
        try {
            portfolio.removeStocks(stock, quantity);
        } catch (InsufficientStockException e) {
            return BigDecimal.ZERO;
        }

        return stockExchangeClient.submitSell(stock, quantity);
    }
}
