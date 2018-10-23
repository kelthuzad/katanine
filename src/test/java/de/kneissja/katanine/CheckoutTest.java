package de.kneissja.katanine;

import de.kneissja.katanine.checkout.Checkout;
import de.kneissja.katanine.checkout.CheckoutFactory;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemInventory;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleSet;
import de.kneissja.katanine.pricingrule.PricingRuleSetFactory;
import de.kneissja.katanine.pricingrule.rules.DefaultPricingRule;
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
        itemInventory.addItem(ItemIdentifier.A, new Price(50))
                .addItem(ItemIdentifier.B, new Price(30))
                .addItem(ItemIdentifier.C, new Price(20))
                .addItem(ItemIdentifier.D, new Price(15));

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
        assertEquals(0, price("").getPriceValue());
        assertEquals(50, price("A").getPriceValue());
        assertEquals(80, price("AB").getPriceValue());
        assertEquals(115, price("CDBA").getPriceValue());

        assertEquals(100, price("AA").getPriceValue());
        assertEquals(130, price("AAA").getPriceValue());
        assertEquals(180, price("AAAA").getPriceValue());
        assertEquals(230, price("AAAAA").getPriceValue());
        assertEquals(260, price("AAAAAA").getPriceValue());

        assertEquals(160, price("AAAB").getPriceValue());
        assertEquals(175, price("AAABB").getPriceValue());
        assertEquals(190, price("AAABBD").getPriceValue());
        assertEquals(190, price("DABABA").getPriceValue());
    }

    public void testIncremental() {
        assertEquals(  0, checkout.getTotal().getPriceValue());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals( 50, checkout.getTotal().getPriceValue());
        checkout.scan(itemInventory.findItem(ItemIdentifier.B));
        assertEquals( 80, checkout.getTotal().getPriceValue());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals(130, checkout.getTotal().getPriceValue());
        checkout.scan(itemInventory.findItem(ItemIdentifier.A));
        assertEquals(160, checkout.getTotal().getPriceValue());
        checkout.scan(itemInventory.findItem(ItemIdentifier.B));
        assertEquals(175, checkout.getTotal().getPriceValue());
    }
}
