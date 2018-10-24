package de.kneissja.katanine.pricingrule.rules;

import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.Item;

import java.util.Collection;

/**
 * Simple pricing rule. Just adds all prices of the items.
 */
public class SimplePricingRule implements PricingRule {

    @Override
    public PricingRule setNextPricingRule(PricingRule nextPricingRule) {
        // is not used because this rule calculates all item values
        return this;
    }

    @Override
    public Price calculatePrice(Collection<Item> items, Price basePrice) {
        Price calculatedPrice = basePrice;
        for (Item item: items) {
            calculatedPrice = calculatedPrice.add(item.getPrice()); // add all item prices together
        }
        return calculatedPrice;
    }
}
