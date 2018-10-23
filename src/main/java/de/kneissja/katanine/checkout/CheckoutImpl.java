package de.kneissja.katanine.checkout;

import de.kneissja.katanine.pricingrule.PricingRuleSet;
import de.kneissja.katanine.Price;
import de.kneissja.katanine.item.Item;

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
