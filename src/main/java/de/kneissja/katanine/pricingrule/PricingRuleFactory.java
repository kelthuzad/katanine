package de.kneissja.katanine.pricingrule;

import de.kneissja.katanine.pricingrule.rules.DefaultPricingRule;
import de.kneissja.katanine.pricingrule.rules.XItemsCostYPricingRule;

public class PricingRuleFactory {

    public PricingRule simplePricingRule() {
        return new DefaultPricingRule();
    }

    public PricingRule xItemsCostYPricingRule() {
        return new XItemsCostYPricingRule(null, null);
    }
}
