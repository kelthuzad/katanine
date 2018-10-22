package de.kneissja.katanine.api;

/**
 * The price of an item
 */
public interface Price {

    /**
     * Returns the price
     * @return the price
     */
    int getPrice();

    /**
     * Adds the prices
     * @param price price to add to this price
     */
    void add(Price price);
}
