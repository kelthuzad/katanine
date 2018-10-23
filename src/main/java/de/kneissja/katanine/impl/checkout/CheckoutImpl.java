package de.kneissja.katanine.impl.checkout;

import de.kneissja.katanine.api.Checkout;
import de.kneissja.katanine.api.PricingRuleSet;
import de.kneissja.katanine.impl.Price;
import de.kneissja.katanine.impl.item.Item;

import java.util.ArrayList;
import java.util.List;

public class CheckoutImpl implements Checkout {
    private PricingRuleSet pricingRuleSet;
    private List<Item> scannedItems = new ArrayList<>();

    public CheckoutImpl(PricingRuleSet pricingRuleSet) {
        this.pricingRuleSet = pricingRuleSet;
    }

    @Override
    public void scan(Item item) {
        scannedItems.add(item);
    }

    @Override
    public Price getTotal() {
        return pricingRuleSet.calculatePrice(scannedItems);
    }
}
