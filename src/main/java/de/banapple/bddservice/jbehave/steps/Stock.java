package de.banapple.bddservice.jbehave.steps;

public class Stock {

    private double threshold;
    private String symbol;
    private double price;

    public Stock(String symbol, double threshold) {

        this.symbol = symbol;
        this.threshold = threshold;
    }

    public void tradeAt(double price) {
        
        this.price = price;
    }

    public StockStatus getStatus() {

        return new StockStatus(this);
    }

    public String getSymbol() {
        
        return symbol;
    }

    public double getPrice() {
        return price;
    }
    
    public double getThreshold() {
        return threshold;
    }
}
