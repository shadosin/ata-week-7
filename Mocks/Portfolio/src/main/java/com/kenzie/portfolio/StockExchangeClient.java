package com.kenzie.portfolio;

import java.math.BigDecimal;

/**
 * Client to connect with the StockExchange.
 */
public class StockExchangeClient {
    private StockExchange stockExchange;

    public StockExchangeClient(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    /**
     * Get the current price of a stock.
     * @param stock Stock to check the price of.
     * @return Current market value of a single share of the stock.
     */
    public BigDecimal getPrice(Stock stock) {
        try {
            StockPriceResponse response = stockExchange.getMarketPrice(stock.getSymbol());

            if (response != null) {
                return response.getPrice();
            } else {
                return null;
            }

        } catch (NonExistentStockException e) {
            return null;
        }
    }

    /**
     * Send a request to the StockExchange to buy stocks.
     * @param stock Which stock to purchase.
     * @param quantity How many shares of the stock to purchase.
     * @return Cost of buying the shares or null if the stock symbol was invalid.
     */
    public BigDecimal submitBuy(Stock stock, int quantity) {
        BuyStockRequest request = BuyStockRequest.builder()
            .withSymbol(stock.getSymbol())
            .withQuantity(quantity)
            .build();

        try {
            BuyStockResponse response = stockExchange.bid(request);
            return response.getPrice();
        } catch (NonExistentStockException e) {
            return null;
        }
    }

    /**
     * Sell stocks on the stock exchange.
     * @param stock Which stock to sell.
     * @param quantity The number of shares to sell.
     * @return USD received for selling the stocks or null if the stock symbol was invalid.
     */
    public BigDecimal submitSell(Stock stock, int quantity) {
        SellStockRequest request = SellStockRequest.builder()
            .withSymbol(stock.getSymbol())
            .withQuantity(quantity)
            .build();

        try {
            SellStockResponse response = stockExchange.offer(request);
            return response.getPrice();
        } catch (NonExistentStockException e) {
            return null;
        }
    }
}
