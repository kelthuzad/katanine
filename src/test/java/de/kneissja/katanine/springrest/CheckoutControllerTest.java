package de.kneissja.katanine.springrest;

import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.pricingrule.PricingRuleService;
import de.kneissja.katanine.pricingrule.rules.SimplePricingRule;
import de.kneissja.katanine.pricingrule.rules.XItemsCostYPricingRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CheckoutControllerTest {

    @Autowired
    private CheckoutController checkoutController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PricingRuleService pricingRuleService;

    @Autowired
    private ItemService itemService;

    @Before
    public void initCheckout() {
        itemService.addItem(ItemIdentifier.A, new Price(50))
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

    @Test
    public void contexLoads() throws Exception {
        assertThat(checkoutController).isNotNull();
    }

    @Test
    public void noScannedItems() throws Exception {
        this.mockMvc.perform(get("/checkout/scan"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        this.mockMvc.perform(get("/checkout/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"priceValue\":0}"));
    }

    @Test
    public void oneItemScanned() throws Exception {
        this.mockMvc.perform(post("/checkout/scan")
                .contentType("text/plain")
                .content("A"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"A\",\"price\":{\"priceValue\":50}}]"));

        this.mockMvc.perform(get("/checkout/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"priceValue\":50}"));
    }

    @Test
    public void deleteScannedItems() throws Exception {
        this.mockMvc.perform(post("/checkout/scan")
                .contentType("text/plain")
                .content("A"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"A\",\"price\":{\"priceValue\":50}}]"));

        this.mockMvc.perform(get("/checkout/scan"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"A\",\"price\":{\"priceValue\":50}}]"));

        this.mockMvc.perform(delete("/checkout/scan"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/checkout/scan"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
