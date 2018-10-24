package de.kneissja.katanine.checkout;

import de.kneissja.katanine.pricingrule.PricingRule;

/**
 * Creates new checkout instances
 */
public class CheckoutFactory {

    /**
     * Creates a new checkout instance
     * @param pricingRule rule that determines how prices are calculated
     * @return a checkout that can calculate the price of items
     */
    public Checkout createCheckout(PricingRule pricingRule) {
        return new CheckoutImpl(pricingRule);
    }
}
