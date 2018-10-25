package de.kneissja.katanine.item;

import de.kneissja.katanine.price.Price;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemServiceTest {

    @Test
    public void testItemInventory() {
        ItemService inventory = new ItemService();

        inventory.addItem(ItemIdentifier.A, new Price(1));
        inventory.addItem(ItemIdentifier.B, new Price(2));

        assertEquals(ItemIdentifier.A, inventory.findItem(ItemIdentifier.A).getIdentifier());
        assertEquals(1, inventory.findItem(ItemIdentifier.A).getPrice().getPriceValue());
        assertEquals(ItemIdentifier.B, inventory.findItem(ItemIdentifier.B).getIdentifier());
        assertEquals(2, inventory.findItem(ItemIdentifier.B).getPrice().getPriceValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testItemInventory_double_entry() {
        ItemService inventory = new ItemService();

        inventory.addItem(ItemIdentifier.A, new Price(1));
        inventory.addItem(ItemIdentifier.A, new Price(1));
    }
}
