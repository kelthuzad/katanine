package de.kneissja.katanine.item;


import de.kneissja.katanine.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates new items to be scanned at the checkout
 */
public class ItemInventory {

    private Map<ItemIdentifier, Item> inventory = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ItemInventory.class);

    /**
     * Adds a new item to the inventory
     * @param identifier (unique) identifier of the item
     * @param itemPrice price of the item
     * @return the current ItemInventory instance, useful as fluent interface
     * @throws IllegalArgumentException if there is already an item with the provided identifier in the inventory
     */
    public ItemInventory addItem(ItemIdentifier identifier, Price itemPrice) {

        if (inventory.containsKey(identifier)) {
            throw new IllegalArgumentException("There is already an item with identifier "+identifier+ " in the inventory");
        }

        ItemImpl item = new ItemImpl(identifier, itemPrice);
        logger.debug("{} was added to the item inventory", item);

        inventory.put(identifier, item);
        return this;
    }

    /**
     * Creates a new item from the identifier
     * @param identifier identifier of the item
     * @return The item that was found
     */
    public Item findItem(ItemIdentifier identifier) {
        return inventory.get(identifier);
    }
}
