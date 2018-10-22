package de.kneissja.katanine.api;

/**
 * An item to be sold at the checkout
 */
public interface Item {
    /**
     * The idenfitier of the item
     * @return identifier
     */
    String getIdentifier();

    /**
     * The price of a single item
     * @return price of the item
     */
    Price getPrice();
}
