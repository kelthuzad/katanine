package de.kneissja.katanine;

import de.kneissja.katanine.api.*;
import de.kneissja.katanine.impl.PriceImpl;
import de.kneissja.katanine.impl.checkout.CheckoutFactory;
import de.kneissja.katanine.impl.item.Item;
import de.kneissja.katanine.impl.item.ItemIdentifier;
import de.kneissja.katanine.impl.item.ItemInventory;
import de.kneissja.katanine.impl.pricingrule.PricingRuleSetFactory;
import de.kneissja.katanine.impl.pricingrule.rules.DefaultPricingRule;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CheckoutTest {

    private ItemInventory itemInventory;
    private Checkout checkout;

    @Before
    public void preTest() {
        itemInventory =  new ItemInventory();
        itemInventory.addItem(ItemIdentifier.A, new PriceImpl(50))
                .addItem(ItemIdentifier.B, new PriceImpl(30))
                .addItem(ItemIdentifier.C, new PriceImpl(20))
                .addItem(ItemIdentifier.D, new PriceImpl(15));

        List<PricingRule> rules = Arrays.asList(new DefaultPricingRule());
        PricingRuleSet pricingRuleSet = new PricingRuleSetFactory().createPricingRuleSet(rules);
        checkout = new CheckoutFactory().createCheckout(pricingRuleSet);
    }

    private List<Item> items(final String goods) {
        List<String> itemNamesList = Arrays.asList(goods.split(""));

        return itemNamesList.stream().map(ItemIdentifier::valueOf)
                .map(itemInventory::findItem)
                .collect(Collectors.toList());
    }

    private Price price(final String goods) {
        List<Item> items = items(goods);
        items.forEach(checkout::scan);
        return checkout.getTotal();
    }

    @Test
    public void testTotals() {
        assertEquals(0, price("").getPrice());
        assertEquals(50, price("A").getPrice());
        assertEquals(80, price("AB").getPrice());
        assertEquals(115, price("CDBA").getPrice());

        assertEquals(100, price("AA").getPrice());
        assertEquals(130, price("AAA").getPrice());
        assertEquals(180, price("AAAA").getPrice());
        assertEquals(230, price("AAAAA").getPrice());
        assertEquals(260, price("AAAAAA").getPrice());

        assertEquals(160, price("AAAB").getPrice());
        assertEquals(175, price("AAABB").getPrice());
        assertEquals(190, price("AAABBD").getPrice());
        assertEquals(190, price("DABABA").getPrice());
    }

    public void testIncremental() {
        assertEquals(  0, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals( 50, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem(ItemIdentifier.B));
        assertEquals( 80, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals(130, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals(160, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem(ItemIdentifier.B));
        assertEquals(175, checkout.getTotal().getPrice());
    }
}
