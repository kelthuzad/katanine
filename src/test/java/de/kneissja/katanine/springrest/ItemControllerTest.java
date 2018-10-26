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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemControllerTest {

    @Autowired
    private ItemController itemController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contexLoads() throws Exception {
        assertThat(itemController).isNotNull();
    }

    @Test
    public void noItems() throws Exception {
        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void getAllExistingItems() throws Exception {

        this.mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"identifier\": \"A\",\"price\": 10}"))
        .andExpect(status().isCreated());

        this.mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"identifier\": \"B\",\"price\": 20}"))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"A\",\"price\":{\"priceValue\":10}},{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"B\",\"price\":{\"priceValue\":20}}]"));
    }

    @Test
    public void getOneExistingItem() throws Exception {

        this.mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"identifier\": \"A\",\"price\": 10}"))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/items/A"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"@class\":\"de.kneissja.katanine.item.ItemImpl\",\"identifier\":\"A\",\"price\":{\"priceValue\":10}}"));
    }
}
