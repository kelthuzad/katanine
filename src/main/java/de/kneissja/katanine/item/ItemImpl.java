package de.kneissja.katanine.item;

import de.kneissja.katanine.Price;

public class ItemImpl implements Item {

    private ItemIdentifier identifier;
    private Price price;

    public ItemImpl(ItemIdentifier identifier, Price price) {
        this.identifier = identifier;
        this.price = price;
    }

    @Override
    public ItemIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public Price getPrice() {
        return price;
    }
}
