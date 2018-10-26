package de.kneissja.katanine.item;


import de.kneissja.katanine.price.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Creates new items to be scanned at the checkout
 */
@Service
public class ItemService {

    private Map<ItemIdentifier, Item> inventory = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    /**
     * Adds a new item to the inventory
     * @param identifier (unique) identifier of the item
     * @param itemPrice price of the item
     * @return the current ItemService instance, useful as fluent interface
     * @throws IllegalArgumentException if there is already an item with the provided identifier in the inventory
     */
    public ItemService addItem(ItemIdentifier identifier, Price itemPrice) {

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

    /**
     * Returns all items in the inventory
     * @return An unmodifiable collection of all items
     */
    public Collection<Item> findAllItems() {
        return Collections.unmodifiableCollection(inventory.values());
    }

    /**
     * Empties the inventory
     */
    public void clearItems() {
        inventory.clear();
    }
}
