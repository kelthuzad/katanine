package de.kneissja.katanine.api;

import java.util.Collection;

/**
 * The rule describing how the price of a specific kind of item should be calculated
 */
public interface PricingRule {
    /**
     * Calculates the price of the provided items
     * @param items A number of items
     * @return The total price of the items
     */
    Price calculatePrice(Collection<Item> items);
}
