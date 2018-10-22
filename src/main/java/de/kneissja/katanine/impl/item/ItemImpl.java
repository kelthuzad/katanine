package de.kneissja.katanine.impl.item;

import de.kneissja.katanine.api.Item;
import de.kneissja.katanine.api.Price;

public class ItemImpl implements Item {

    private String identifier;
    private Price price;

    public ItemImpl(String identifier, Price price) {
        this.identifier = identifier;
        this.price = price;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Price getPrice() {
        return price;
    }
}
