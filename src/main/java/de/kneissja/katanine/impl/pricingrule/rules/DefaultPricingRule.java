package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.api.PricingRule;
import de.kneissja.katanine.impl.Price;
import de.kneissja.katanine.impl.item.Item;

import java.util.Collection;

/**
 * Default pricing rule. Just adds all prices.
 */
public class DefaultPricingRule implements PricingRule {

    @Override
    public Price calculatePrice(Collection<Item> items, Price basePrice) {
        Price calculatedPrice = basePrice;
        for (Item item: items) {
            calculatedPrice = calculatedPrice.add(item.getPrice());
        }
        return calculatedPrice;
    }
}
