package de.kneissja.katanine;

import de.kneissja.katanine.checkout.Checkout;
import de.kneissja.katanine.checkout.CheckoutImpl;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemInventory;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.rules.DefaultPricingRule;
import de.kneissja.katanine.pricingrule.rules.XItemsCostYPricingRule;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CheckoutTest {

    private ItemInventory itemInventory;

    public Checkout createCheckout() {
        itemInventory =  new ItemInventory();
        itemInventory.addItem(ItemIdentifier.A, new Price(50))
                .addItem(ItemIdentifier.B, new Price(30))
                .addItem(ItemIdentifier.C, new Price(20))
                .addItem(ItemIdentifier.D, new Price(15));

        Map<ItemIdentifier, Map<Integer, Price>> xItemsCostYRules = new HashMap<>();
        Map<Integer, Price> itemAPricingRule = new HashMap<>();
        itemAPricingRule.put(3, new Price(130));
        Map<Integer, Price> itemBPricingRule = new HashMap<>();
        itemBPricingRule.put(2, new Price(45));
        xItemsCostYRules.put(ItemIdentifier.A, itemAPricingRule);
        xItemsCostYRules.put(ItemIdentifier.B, itemBPricingRule);

        PricingRule pricingRule = new XItemsCostYPricingRule(xItemsCostYRules)
                .setNextPricingRule(new DefaultPricingRule());

        return new CheckoutImpl(pricingRule);
    }

    private List<Item> items(final String goods) {
        if (goods.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> itemNamesList = Arrays.asList(goods.split(""));

        return itemNamesList.stream().map(ItemIdentifier::valueOf)
                .map(itemInventory::findItem)
                .collect(Collectors.toList());
    }

    private Price price(final String goods) {
        Checkout checkout = createCheckout();
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
        Checkout checkout = createCheckout();
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
