package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.impl.item.Item;
import de.kneissja.katanine.api.Price;
import de.kneissja.katanine.impl.PriceImpl;
import de.kneissja.katanine.impl.item.ItemImpl;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XItemsCostYPricingRuleTest {

    @Test
    public void testXImtesCostYPricingRule() {
        Map<String, Map<Integer, Price>> xcostyPricingRules = new HashMap<>();
        Map<Integer, Price> xItemACostYPricingRules = new HashMap<>();
        xItemACostYPricingRules.put(2, new PriceImpl(90));
        xItemACostYPricingRules.put(3, new PriceImpl(130));
        Map<Integer, Price> xItemBCostYPricingRules = new HashMap<>();
        xItemBCostYPricingRules.put(2, new PriceImpl(45));
        xItemBCostYPricingRules.put(5, new PriceImpl(100));
        xcostyPricingRules.put("A", xItemACostYPricingRules);
        xcostyPricingRules.put("B", xItemBCostYPricingRules);

        XItemsCostYPricingRule rule = new XItemsCostYPricingRule(xcostyPricingRules);

        List<Item> itemsToCalculate = Arrays.asList(new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("B", new PriceImpl(30)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("B", new PriceImpl(30)),
                new ItemImpl("B", new PriceImpl(30)),
                new ItemImpl("A", new PriceImpl(50)),
                new ItemImpl("A", new PriceImpl(50)));

        Price basePrice = new PriceImpl(0);
        Price endPrice = rule.calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(425, endPrice.getPrice());
    }
}
