package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.impl.Price;
import de.kneissja.katanine.impl.item.Item;
import de.kneissja.katanine.impl.item.ItemIdentifier;
import de.kneissja.katanine.impl.item.ItemImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XItemsCostYPricingRuleTest {

    @Test
    public void testXImtesCostYPricingRule() {
        Map<ItemIdentifier, Map<Integer, Price>> xcostyPricingRules = new HashMap<>();
        Map<Integer, Price> xItemACostYPricingRules = new HashMap<>();
        xItemACostYPricingRules.put(2, new Price(90));
        xItemACostYPricingRules.put(3, new Price(130));
        Map<Integer, Price> xItemBCostYPricingRules = new HashMap<>();
        xItemBCostYPricingRules.put(2, new Price(45));
        xItemBCostYPricingRules.put(5, new Price(100));
        xcostyPricingRules.put(ItemIdentifier.A, xItemACostYPricingRules);
        xcostyPricingRules.put(ItemIdentifier.B, xItemBCostYPricingRules);

        XItemsCostYPricingRule rule = new XItemsCostYPricingRule(xcostyPricingRules);

        List<Item> itemsToCalculate = Arrays.asList(new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.B, new Price(30)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.B, new Price(30)),
                new ItemImpl(ItemIdentifier.B, new Price(30)),
                new ItemImpl(ItemIdentifier.A, new Price(50)),
                new ItemImpl(ItemIdentifier.A, new Price(50)));

        Price basePrice = new Price(0);
        Price endPrice = rule.calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(425, endPrice.getPriceValue());
    }
}
