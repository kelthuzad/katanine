package de.kneissja.katanine.pricingrule.rules;

import de.kneissja.katanine.item.ItemService;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimplePricingRuleTest {
    private ItemService inventory;

    @Before
    public void init() {
        inventory = new ItemService()
                .addItem(ItemIdentifier.A, new Price(50))
                .addItem(ItemIdentifier.B, new Price(30))
                .addItem(ItemIdentifier.C, new Price(20))
                .addItem(ItemIdentifier.D, new Price(15));
    }

    @Test
    public void testDefaultPricingRule_empty() {
        List<Item> itemsToCalculate = Collections.emptyList();

        Price basePrice = new Price(0);
        Price endPrice = new SimplePricingRule().calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(0, endPrice.getPriceValue());
    }

    @Test
    public void testDefaultPricingRule() {
        List<Item> itemsToCalculate = Arrays.asList(
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.C),
                inventory.findItem(ItemIdentifier.D),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.A),
                inventory.findItem(ItemIdentifier.C),
                inventory.findItem(ItemIdentifier.B),
                inventory.findItem(ItemIdentifier.D),
                inventory.findItem(ItemIdentifier.A));

        Price basePrice = new Price(0);
        Price endPrice = new SimplePricingRule().calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(380, endPrice.getPriceValue());
    }
}
