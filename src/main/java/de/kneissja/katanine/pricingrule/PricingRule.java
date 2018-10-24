package de.kneissja.katanine.pricingrule;

import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.Item;

import java.util.Collection;

/**
 * The rule describing how the price of a specific kind of item should be calculated
 */
public interface PricingRule {

    /**
     * Sets a rule that will be used to calculate the price of all items that could not be calculated by applying the current rule
     * @param nextPricingRule rule to calculate the price of all items that could not be calculated
     * @return the current rule, to support fluent interface usage
     */
    PricingRule setNextPricingRule(PricingRule nextPricingRule);

    /**
     * Calculates the price of the provided items by using the current and if required the defined nextPricingRule
     * @param items A number of items
     * @param basePrice Base price to which all calculated prices will be added
     * @return The total price of the items
     */
    Price calculatePrice(Collection<Item> items, Price basePrice);
}
