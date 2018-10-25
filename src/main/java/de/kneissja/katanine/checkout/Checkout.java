package de.kneissja.katanine.checkout;

import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.price.Price;

import java.util.List;

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

    /**
     * Returns all items that were scanned so far
     * @return immutable list of the items
     */
    List<Item> getScannedItems();

    /**
     * Deletes all items that were scanned so far
     */
    void deleteScannedItems();
}
