package de.kneissja.katanine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PriceTest {

    @Test
    public void testPrice_init() {
        assertEquals(0, new Price(0).getPriceValue());
        assertEquals(10, new Price(10).getPriceValue());
        assertEquals(25, new Price((new Price(25))).getPriceValue());
    }

    @Test
    public void testPrice_add() {
        Price p = new Price(1);
        Price p2 = new Price(2);

        assertNotEquals(p, p.add(p2));
        assertEquals(3, p.add(p2).getPriceValue());
    }

    @Test
    public void testPrice_multiply() {
        Price p = new Price(2);

        assertNotEquals(p, p.multiply(3));
        assertEquals(6, p.multiply(3).getPriceValue());
    }
}
