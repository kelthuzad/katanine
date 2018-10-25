package de.kneissja.katanine.springrest;

import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingRuleController {

    @Autowired
    private PricingRuleService pricingRuleService;

    @RequestMapping(value = "/pricingrule", method = RequestMethod.POST)
    public PricingRule setPricingRule(@RequestBody PricingRule pricingRule) {
        pricingRuleService.setPricingRule(pricingRule);
        return pricingRule;
    }

    @RequestMapping(value = "/pricingrule", method = RequestMethod.GET)
    public PricingRule getPricingRule() {
        return pricingRuleService.getPricingRule();
    }
}
