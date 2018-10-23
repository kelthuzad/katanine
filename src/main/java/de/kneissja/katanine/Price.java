package de.kneissja.katanine;

/**
 * Model of an item price. This class is immutable.
 */
public class Price {

    /**
     * The value of the price
     */
    private int priceValue;

    /**
     * Creates a new price
     * @param price value of the price
     */
    public Price(int price) {
        this.priceValue = price;
    }

    /**
     * Creates a new price
     * @param price price that should be used as template
     */
    public Price(Price price) {
        this.priceValue = price.getPriceValue();
    }

    /**
     * The value of the price
     * @return price value
     */
    public int getPriceValue() {
        return priceValue;
    }

    /**
     * Creates a new price by adding the provided price to the current price.
     * The current price object will not be modified!
     * @param price price to add to this price
     * @return a new price that has the added value of both prices
     */
    public Price add(Price price) {
        return new Price(priceValue+price.getPriceValue());
    }

    /**
     * Creates a new price by multiplying the provided price with the provided value.
     * The current price object will not be modified!
     * @param multiplier factor with which the current price will be multiplied
     * @return a new price that has the multiplied value
     */
    public Price multiply(int multiplier) {
        return new Price(priceValue*multiplier);
    }

    @Override
    public String toString() {
        return "Price (value "+priceValue+")";
    }
}
