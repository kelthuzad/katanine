package de.kneissja.katanine.pricingrule.rules;

import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemInventory;
import de.kneissja.katanine.pricingrule.PricingRule;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XItemsCostYPricingRuleTest {

    private ItemInventory inventory;

    @Before
    public void init() {
        inventory = new ItemInventory()
                .addItem(ItemIdentifier.A, new Price(50))
                .addItem(ItemIdentifier.B, new Price(30))
                .addItem(ItemIdentifier.C, new Price(20))
                .addItem(ItemIdentifier.D, new Price(15));
    }

    @Test
    public void testXImtesCostYPricingRule_noRules() {
        Map<ItemIdentifier, Map<Integer, Price>> xcostyPricingRules = new HashMap<>();
        PricingRule rule = new XItemsCostYPricingRule(xcostyPricingRules).setNextPricingRule(new SimplePricingRule());

        List<Item> itemsToCalculate = Arrays.asList(
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.C),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.D),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.D),
                inventory.findItem(ItemIdentifier.C),
                inventory.findItem(ItemIdentifier.C));

        Price basePrice = new Price(0);
        Price endPrice = rule.calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(330, endPrice.getPriceValue());
    }

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

        PricingRule rule = new XItemsCostYPricingRule(xcostyPricingRules).setNextPricingRule(new SimplePricingRule());

        List<Item> itemsToCalculate = Arrays.asList(
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A));

        Price basePrice = new Price(0);
        Price endPrice = rule.calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(425, endPrice.getPriceValue());
    }
}
