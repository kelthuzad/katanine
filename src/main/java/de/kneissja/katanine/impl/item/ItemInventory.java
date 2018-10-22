package de.kneissja.katanine.impl.item;

import de.kneissja.katanine.api.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates new items to be scanned at the checkout
 */
public class ItemInventory {

    private Map<String, Item> inventory = new HashMap<>();

    public void addItems(Item... items) {
        inventory.putAll(Stream.of(items).collect(Collectors.toMap(Item::getIdentifier, item -> item)));
    }

    /**
     * Creates a new item from the identifier
     * @param identifier identifier of the item
     * @return The item that was found
     */
    public Item findItem(String identifier) {
        return inventory.get(identifier);
    }
}
