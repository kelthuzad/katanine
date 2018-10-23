package de.kneissja.katanine.impl.item;

import de.kneissja.katanine.impl.Price;

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
