package de.kneissja.katanine.springrest;

import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingRuleController {

    @Autowired
    private PricingRuleService pricingRuleService;

    @RequestMapping(value = "/pricingrule", method = RequestMethod.POST)
    public ResponseEntity<PricingRule> setPricingRule(@RequestBody PricingRule pricingRule) {

        if (pricingRule == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        pricingRuleService.setPricingRule(pricingRule);
        return new ResponseEntity<>(pricingRule, HttpStatus.OK);
    }

    @RequestMapping(value = "/pricingrule", method = RequestMethod.GET)
    public ResponseEntity<PricingRule> getPricingRule() {
        PricingRule pricingRule = pricingRuleService.getPricingRule();

        if (pricingRule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pricingRule, HttpStatus.OK);
    }
}
