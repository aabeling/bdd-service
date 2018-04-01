package de.banapple.bddservice.jbehave.steps;

public class StockStatus {

    private Stock stock;

    public StockStatus(Stock stock) {

        this.stock = stock;
    }

    public String name() {

        if (stock.getPrice() > stock.getThreshold()) {
            return "ON";
        } else {
            return "OFF";
        }
    }

}
