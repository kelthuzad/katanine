package de.kneissja.katanine.springrest;

import de.kneissja.katanine.checkout.CheckoutService;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import de.kneissja.katanine.price.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckoutController {

    @Autowired
    ItemService inventory;

    @Autowired
    CheckoutService checkoutService;

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.POST)
    public List<Item> scanItems(@RequestBody String identifierName) {
        ItemIdentifier identifier = ItemIdentifier.valueOf(identifierName);
        Item item = inventory.findItem(identifier);
        checkoutService.scan(item);
        return checkoutService.getScannedItems();
    }

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.GET)
    public List<Item> getScannedItems() {
        return checkoutService.getScannedItems();
    }

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.DELETE)
    public void resetCheckout() {
        checkoutService.deleteScannedItems();
    }

    @RequestMapping(value = "/checkout/total", method = RequestMethod.GET)
    public Price getTotal() {
        return checkoutService.getTotal();
    }
}
