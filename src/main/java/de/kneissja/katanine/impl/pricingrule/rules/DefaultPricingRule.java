package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.api.Item;
import de.kneissja.katanine.api.Price;
import de.kneissja.katanine.api.PricingRule;
import de.kneissja.katanine.impl.PriceImpl;

import java.util.Collection;

/**
 * Default pricing rule. Just adds all prices.
 */
public class DefaultPricingRule implements PricingRule {

    @Override
    public Price calculatePrice(Collection<Item> items) {
        Price price = new PriceImpl(0);
        items.forEach(item -> price.add(item.getPrice()));
        return price;
    }
}
