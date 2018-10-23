package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.api.Item;
import de.kneissja.katanine.api.Price;
import de.kneissja.katanine.api.PricingRule;

import java.util.Collection;

/**
 * Default pricing rule. Just adds all prices.
 */
public class DefaultPricingRule implements PricingRule {

    @Override
    public Price calculatePrice(Collection<Item> items, Price basePrice) {
        items.forEach(item -> basePrice.add(item.getPrice()));
        return basePrice;
    }
}
