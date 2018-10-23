package de.kneissja.katanine.pricingrule;

import java.util.Collection;

/**
 * Creates Rulesets for item pricing
 */
public class PricingRuleSetFactory {
    /**
     * Creates a new ruleset for item pricing
     * @param pricingRules all rules that should be applied for price calculation
     * @return ruleset describing all item pricing rules
     */
    public PricingRuleSet createPricingRuleSet(Collection<PricingRule> pricingRules) {
        return new PricingRuleSetImpl(pricingRules);
    }
}
