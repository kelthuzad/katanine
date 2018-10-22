package de.kneissja.katanine.impl;

import de.kneissja.katanine.api.Price;

public class PriceImpl implements Price {

    private int price;

    public PriceImpl(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void add(Price price) {
        this.price += price.getPrice();
    }
}
