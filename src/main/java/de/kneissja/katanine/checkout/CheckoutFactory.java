package de.kneissja.katanine.checkout;

import de.kneissja.katanine.pricingrule.PricingRuleSet;

/**
 * Creates a new checkout
 */
public class CheckoutFactory {

    /**
     * Creates a new checkout
     * @param pricingRuleSet Set of rules for the price calculation
     * @return a checkout that calculates prices for items
     */
    public Checkout createCheckout(PricingRuleSet pricingRuleSet) {
        return new CheckoutImpl(pricingRuleSet);
    }
}
