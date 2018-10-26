package de.kneissja.katanine;

import de.kneissja.katanine.checkout.CheckoutService;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleService;
import de.kneissja.katanine.pricingrule.rules.SimplePricingRule;
import de.kneissja.katanine.pricingrule.rules.XItemsCostYPricingRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutTest {

    @Autowired
    private ItemService inventory;

    @Autowired
    private PricingRuleService pricingRuleService;

    @Autowired
    private CheckoutService checkoutService;

    @Before
    public void init() {
        inventory.addItem(ItemIdentifier.A, new Price(50))
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
                .setNextPricingRule(new SimplePricingRule());

        pricingRuleService.setPricingRule(pricingRule);
    }

    @After
    public void cleanup() {
        inventory.clearItems();
        checkoutService.deleteScannedItems();
    }

    private List<Item> items(final String goods) {
        if (goods.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> itemNamesList = Arrays.asList(goods.split(""));

        return itemNamesList.stream().map(ItemIdentifier::valueOf)
                .map(inventory::findItem)
                .collect(Collectors.toList());
    }

    private Price price(final String goods) {
        List<Item> items = items(goods);
        items.forEach(checkoutService::scan);
        Price total = checkoutService.getTotal();
        checkoutService.deleteScannedItems();
        return total;
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

    @Test
    public void testIncremental() {
        assertEquals(  0, checkoutService.getTotal().getPriceValue());
        checkoutService.scan(inventory.findItem(ItemIdentifier.A));
        assertEquals( 50, checkoutService.getTotal().getPriceValue());
        checkoutService.scan(inventory.findItem(ItemIdentifier.B));
        assertEquals( 80, checkoutService.getTotal().getPriceValue());
        checkoutService.scan(inventory.findItem(ItemIdentifier.A));
        assertEquals(130, checkoutService.getTotal().getPriceValue());
        checkoutService.scan(inventory.findItem(ItemIdentifier.A));
        assertEquals(160, checkoutService.getTotal().getPriceValue());
        checkoutService.scan(inventory.findItem(ItemIdentifier.B));
        assertEquals(175, checkoutService.getTotal().getPriceValue());
    }
}
