package de.kneissja.katanine.impl;

import de.kneissja.katanine.api.Price;

public class PriceImpl implements Price {

    private int price;

    public PriceImpl(int price) {
        this.price = price;
    }

    public PriceImpl(Price price) {
        this.price = price.getPrice();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void add(Price price) {
        this.price += price.getPrice();
    }

    @Override
    public void multiply(int multiplier) {
        this.price *= multiplier;
    }
}
