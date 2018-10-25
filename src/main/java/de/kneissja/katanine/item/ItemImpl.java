package de.kneissja.katanine.item;

import de.kneissja.katanine.price.Price;

/**
 * Implementation for an item
 */
class ItemImpl implements Item {

    private ItemIdentifier identifier;
    private Price price;

    /**
     * Creates a new item
     * @param identifier identifier for this item
     * @param price price of this item
     */
    ItemImpl(ItemIdentifier identifier, Price price) {
        if (identifier == null || price == null) {
            throw new IllegalArgumentException("Both identifier and price may not be null");
        }
        this.identifier = identifier;
        this.price = price;
    }

    /**
     * Empty constructor for deserialization
     */
    ItemImpl() {
    }

    @Override
    public ItemIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item "+identifier.name()+", "+price;
    }
}
