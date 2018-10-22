package de.kneissja.katanine;

import de.kneissja.katanine.api.Checkout;
import de.kneissja.katanine.api.Item;
import de.kneissja.katanine.api.Price;
import de.kneissja.katanine.api.PricingRuleSet;
import de.kneissja.katanine.impl.CheckoutFactory;
import de.kneissja.katanine.impl.ItemInventory;
import de.kneissja.katanine.impl.PricingRuleSetFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CheckoutTest {

    private ItemInventory itemInventory = new ItemInventory();
    private CheckoutFactory checkoutFactory = new CheckoutFactory();
    private PricingRuleSetFactory pricingRuleSetFactory = new PricingRuleSetFactory();

    private List<Item> items(final String goods) {
        List<String> itemNamesList = Arrays.asList(goods.split(""));
        return itemNamesList.stream().map(itemInventory::findItem).collect(Collectors.toList());
    }

    private PricingRuleSet pricingRuleSet() {
        return pricingRuleSetFactory.createPricingRuleSet(Collections.emptyList());
    }

    private Price price(final String goods) {
        PricingRuleSet pricingRuleSet = pricingRuleSet();
        Checkout checkout = checkoutFactory.createCheckout(pricingRuleSet);
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
        Checkout checkout = checkoutFactory.createCheckout(pricingRuleSet());

        assertEquals(  0, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem("A"));
        assertEquals( 50, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem("B"));
        assertEquals( 80, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem("A"));
        assertEquals(130, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem("A"));
        assertEquals(160, checkout.getTotal().getPrice());
        checkout.scan(itemInventory.findItem("B"));
        assertEquals(175, checkout.getTotal().getPrice());
    }
}
