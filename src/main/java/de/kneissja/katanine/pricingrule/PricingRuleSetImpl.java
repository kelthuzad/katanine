package de.kneissja.katanine.pricingrule;

import de.kneissja.katanine.Price;
import de.kneissja.katanine.item.Item;

import java.util.ArrayList;
import java.util.Collection;

class PricingRuleSetImpl implements PricingRuleSet {
    private Collection<PricingRule> pricingRules;

    PricingRuleSetImpl(Collection<PricingRule> pricingRules) {
        this.pricingRules = new ArrayList<>(pricingRules);
    }

    @Override
    public Price calculatePrice(Collection<Item> items) {
        return null;
    }
}
