package de.kneissja.katanine.item;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.kneissja.katanine.price.Price;

/**
 * An item to be sold at the checkout
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = de.kneissja.katanine.item.ItemImpl.class)
public interface Item extends Comparable<Item> {
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
