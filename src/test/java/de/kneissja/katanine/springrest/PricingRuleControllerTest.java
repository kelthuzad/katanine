package de.kneissja.katanine.springrest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PricingRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PricingRuleController pricingRuleController;

    @Test
    public void contexLoads() throws Exception {
        assertThat(pricingRuleController).isNotNull();
    }

    @Test
    public void testController() throws Exception {

        this.mockMvc.perform(post("/pricingrule")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                            "\"type\": \"xcosty\"," +
                            "\"priceCalculationMap\": {" +
                                "\"A\": {" +
                                    "\"1\": 15," +
                                    "\"2\": 25" +
                                "}" +
                            "}," +
                            "\"nextPricingRule\": {" +
                                "\"type\": \"simple\"" +
                            "}" +
                        "}"))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/pricingrule"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"type\":\"xcosty\",\"priceCalculationMap\":{\"A\":{\"1\":{\"priceValue\":15},\"2\":{\"priceValue\":25}}},\"nextPricingRule\":{\"type\":\"simple\"}}"));
    }
}
