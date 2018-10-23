package de.kneissja.katanine;

public class Price {

    private int priceValue;

    public Price(int price) {
        this.priceValue = price;
    }

    public Price(Price price) {
        this.priceValue = price.getPriceValue();
    }

    public int getPriceValue() {
        return priceValue;
    }

    public Price add(Price price) {
        return new Price(priceValue+price.getPriceValue());
    }

    public Price multiply(int multiplier) {
        return new Price(priceValue*multiplier);
    }
}
