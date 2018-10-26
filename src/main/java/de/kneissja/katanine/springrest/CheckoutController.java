package de.kneissja.katanine.springrest;

import de.kneissja.katanine.checkout.CheckoutService;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import de.kneissja.katanine.price.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckoutController {

    @Autowired
    private ItemService inventory;

    @Autowired
    private CheckoutService checkoutService;

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.POST)
    public ResponseEntity<List<Item>> scanItems(@RequestBody String identifierName) {

        logger.debug("Received POST request to /checkout/scan");

        if (identifierName == null || identifierName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ItemIdentifier identifier;
        try {
            identifier = ItemIdentifier.valueOf(identifierName);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Item item = inventory.findItem(identifier);

        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        checkoutService.scan(item);
        return new ResponseEntity<>(checkoutService.getScannedItems(), HttpStatus.OK);
    }

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.GET)
    public List<Item> getScannedItems() {
        logger.debug("Received GET request to /checkout/scan");
        return checkoutService.getScannedItems();
    }

    @RequestMapping(value = "/checkout/scan", method = RequestMethod.DELETE)
    public void resetCheckout() {
        logger.debug("Received DELETE request to /checkout/scan");
        checkoutService.deleteScannedItems();
    }

    @RequestMapping(value = "/checkout/total", method = RequestMethod.GET)
    public Price getTotal() {
        logger.debug("Received GET request to /checkout/total");
        return checkoutService.getTotal();
    }
}
