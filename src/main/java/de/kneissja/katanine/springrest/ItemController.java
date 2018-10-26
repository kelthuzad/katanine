package de.kneissja.katanine.springrest;

import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ItemController {

    @Autowired
    private ItemService inventory;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<Void> addItem(@RequestBody Item item) {
        logger.debug("Received POST request to /items");

        if (item == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        inventory.addItem(item.getIdentifier(), item.getPrice());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/items")
    public Collection<Item> getItems() {
        logger.debug("Received GET request to /items");
        return inventory.findAllItems();
    }

    @RequestMapping("/items/{identifier}")
    public ResponseEntity<Item> getItem(@PathVariable ItemIdentifier identifier) {
        logger.debug("Received POST request to /items/{identifier}");
        Item item = inventory.findItem(identifier);

        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
