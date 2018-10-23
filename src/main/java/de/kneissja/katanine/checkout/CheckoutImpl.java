package de.kneissja.katanine.checkout;

import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.Price;
import de.kneissja.katanine.item.Item;

import java.util.ArrayList;
import java.util.List;

public class CheckoutImpl implements Checkout {
    private final PricingRule pricingRule;
    private List<Item> scannedItems = new ArrayList<>();

    public CheckoutImpl(PricingRule pricingRule) {
        this.pricingRule = pricingRule;
    }

    @Override
    public void scan(Item item) {
        scannedItems.add(item);
    }

    @Override
    public Price getTotal() {
        return pricingRule.calculatePrice(scannedItems, new Price(0));
    }
}
