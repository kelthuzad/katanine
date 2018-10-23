package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.impl.item.Item;
import de.kneissja.katanine.api.PricingRule;
import de.kneissja.katanine.impl.Price;
import de.kneissja.katanine.impl.item.ItemIdentifier;

import java.util.*;
import java.util.stream.Collectors;

public class XItemsCostYPricingRule implements PricingRule {

    private Map<ItemIdentifier, Map<Integer, Price>> priceCalculationMap;
    private PricingRule nextPricingRule;

    public XItemsCostYPricingRule(Map<ItemIdentifier, Map<Integer, Price>> priceCalculationMap, PricingRule nextPricingRule) {
        this.priceCalculationMap = priceCalculationMap;
        this.nextPricingRule = nextPricingRule;
    }

    public XItemsCostYPricingRule(Map<ItemIdentifier, Map<Integer, Price>> priceCalculationMap) {
        this.priceCalculationMap = priceCalculationMap;
        this.nextPricingRule = new DefaultPricingRule();
    }

    @Override
    public Price calculatePrice(Collection<Item> itemsToCalculate, Price basePrice) {
        Map<ItemIdentifier, List<Item>> itemsByIdentifier = itemsToCalculate.stream().collect(Collectors.groupingBy(Item::getIdentifier));
        List<Item> uncalculatedItems = new ArrayList<>();

        Price newPrice = basePrice;

        for (Map.Entry<ItemIdentifier, List<Item>> entry: itemsByIdentifier.entrySet()) {
            ItemIdentifier identifier = entry.getKey();
            List<Item> items = entry.getValue();

            if (!priceCalculationMap.containsKey(identifier)) {
                uncalculatedItems.addAll(items);
            }

            Map<Integer, Price> priceMap = priceCalculationMap.get(identifier);
            List<Integer> sortedPriceMapKeys = priceMap.keySet().stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            int numberOfItems = items.size();
            for (Integer priceMapKey : sortedPriceMapKeys) {
                int numberOfTimesThisKeyCanBeApplied = numberOfItems / priceMapKey;
                Price pricePerKeyOccurance = priceMap.get(priceMapKey);
                Price calculatedPrice = pricePerKeyOccurance.multiply(numberOfTimesThisKeyCanBeApplied);
                newPrice = newPrice.add(calculatedPrice);
                numberOfItems -= numberOfTimesThisKeyCanBeApplied * priceMapKey;
            }

            uncalculatedItems.addAll(items.subList(0, numberOfItems));
        }

        return nextPricingRule.calculatePrice(uncalculatedItems, newPrice);
    }
}
