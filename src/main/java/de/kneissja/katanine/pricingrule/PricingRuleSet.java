package de.kneissja.katanine.pricingrule;

import de.kneissja.katanine.Price;
import de.kneissja.katanine.item.Item;

import java.util.Collection;

/**
 * The collection of all pricing rules that describe how the items prices should be calculated
 */
public interface PricingRuleSet {

    /**
     * Calculates the price of all provided items by using its pricing rules
     * @param items Items that should be calculated
     * @return The total price of all the items, according to the rule set
     */
    Price calculatePrice(Collection<Item> items);
}
