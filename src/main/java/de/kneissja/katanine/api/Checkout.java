package de.kneissja.katanine.api;

import de.kneissja.katanine.impl.item.Item;

/**
 * The checkout that calculates the total price
 */
public interface Checkout {
    /**
     * Adds the item to the total price
     * @param item the item to be calculated
     */
    void scan(Item item);

    /**
     * The total price of all items that were scanned
     * @return The price of all scanned items
     */
    Price getTotal();
}
