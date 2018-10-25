package de.kneissja.katanine.pricingrule;

import org.springframework.stereotype.Service;

/**
 * Simple service that stores the selected pricing rule
 */
@Service
public class PricingRuleService {

    private PricingRule pricingRule;

    public void setPricingRule(PricingRule pricingRule) {
        this.pricingRule = pricingRule;
    }

    public PricingRule getPricingRule() {
        return pricingRule;
    }
}
