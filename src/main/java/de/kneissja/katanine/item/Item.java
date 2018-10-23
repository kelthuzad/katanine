package de.kneissja.katanine.item;

import de.kneissja.katanine.Price;

/**
 * An item to be sold at the checkout
 */
public interface Item {
    /**
     * The idenfitier of the item
     * @return identifier
     */
    ItemIdentifier getIdentifier();

    /**
     * The price of a single item
     * @return price of the item
     */
    Price getPrice();
}
