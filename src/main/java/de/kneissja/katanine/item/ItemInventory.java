package de.kneissja.katanine.item;


import de.kneissja.katanine.Price;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates new items to be scanned at the checkout
 */
public class ItemInventory {

    private Map<ItemIdentifier, Item> inventory = new HashMap<>();

    public ItemInventory addItem(ItemIdentifier identifier, Price itemPrice) {

        if (inventory.containsKey(identifier)) {
            throw new IllegalArgumentException("There is already an item with identifier "+identifier+ " in the inventory");
        }

        inventory.put(identifier, new ItemImpl(identifier, itemPrice));
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
