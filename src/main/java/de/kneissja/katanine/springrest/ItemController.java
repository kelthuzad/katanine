package de.kneissja.katanine.springrest;

import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.item.ItemIdentifier;
import de.kneissja.katanine.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ItemController {

    @Autowired
    private ItemService inventory;

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public void addItem(@RequestBody Item item) {
        inventory.addItem(item.getIdentifier(), item.getPrice());
    }

    @RequestMapping("/items")
    public Collection<Item> getItems() {
        return inventory.findAllItems();
    }

    @RequestMapping("/items/{identifier}")
    public Item getItem(@PathVariable ItemIdentifier identifier) {
        return inventory.findItem(identifier);
    }
}
