package de.kneissja.katanine.checkout;

import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.pricingrule.PricingRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the checkout.
 * It can be initialized with a pricing rule and will calculate the price accordingly
 */
class CheckoutImpl implements Checkout {
    private final PricingRule pricingRule;
    private List<Item> scannedItems = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(CheckoutImpl.class);

    /**
     * Creates a new checkout that calculates prices according to the provided rule
     * @param pricingRule the pricing rule that is used to calculate the prices
     * @throws IllegalArgumentException if the pricing rule is null
     */
    CheckoutImpl(PricingRule pricingRule) {
        if (pricingRule == null) {
            throw new IllegalArgumentException("There must be a pricing rule for the checkout");
        }
        this.pricingRule = pricingRule;
    }

    @Override
    public void scan(Item item) {
        logger.debug("Scanned {}", item);
        scannedItems.add(item);
    }

    @Override
    public Price getTotal() {
        logger.debug("Calculating total price for {} items", scannedItems.size());
        return pricingRule.calculatePrice(scannedItems, new Price(0));
    }
}
