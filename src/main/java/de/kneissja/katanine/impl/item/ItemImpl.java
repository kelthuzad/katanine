package de.kneissja.katanine.impl.item;

import de.kneissja.katanine.api.Price;

class ItemImpl implements Item {

    private ItemIdentifier identifier;
    private Price price;

    ItemImpl(ItemIdentifier identifier, Price price) {
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
