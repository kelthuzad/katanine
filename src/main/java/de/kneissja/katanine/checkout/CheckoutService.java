package de.kneissja.katanine.checkout;

import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the checkout.
 * It uses the pricing rule and will calculate the price accordingly
 */
@Service
public class CheckoutService implements Checkout {

    @Autowired
    private PricingRuleService pricingRuleService;
    private List<Item> scannedItems = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(CheckoutService.class);

    @Override
    public void scan(Item item) {
        logger.debug("Scanned {}", item);
        scannedItems.add(item);
    }

    @Override
    public Price getTotal() {
        logger.debug("Calculating total price for {} items", scannedItems.size());
        PricingRule pricingRule = pricingRuleService.getPricingRule();

        if (pricingRule == null) {
            throw new IllegalStateException("No pricing rule set!");
        }

        return pricingRule.calculatePrice(scannedItems, new Price(0));
    }

    @Override
    public List<Item> getScannedItems() {
        return Collections.unmodifiableList(scannedItems);
    }

    @Override
    public void deleteScannedItems() {
        scannedItems.clear();
    }
}
