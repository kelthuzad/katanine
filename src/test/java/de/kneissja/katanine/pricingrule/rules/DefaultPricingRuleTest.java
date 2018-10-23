package de.kneissja.katanine.pricingrule.rules;

import de.kneissja.katanine.Price;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemInventory;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DefaultPricingRuleTest {
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
    public void testDefaultPricingRule_empty() {
        List<Item> itemsToCalculate = Collections.emptyList();

        Price basePrice = new Price(0);
        Price endPrice = new DefaultPricingRule().calculatePrice(itemsToCalculate, basePrice);
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
        Price endPrice = new DefaultPricingRule().calculatePrice(itemsToCalculate, basePrice);
        assertNotNull(endPrice);
        assertEquals(380, endPrice.getPriceValue());
    }
}
