package de.kneissja.katanine.impl.pricingrule;

import de.kneissja.katanine.api.PricingRule;
import de.kneissja.katanine.api.PricingRuleSet;
import de.kneissja.katanine.impl.Price;
import de.kneissja.katanine.impl.item.Item;

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
