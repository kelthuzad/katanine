package de.kneissja.katanine.impl.pricingrule.rules;

import de.kneissja.katanine.api.Item;
import de.kneissja.katanine.api.Price;
import de.kneissja.katanine.api.PricingRule;
import de.kneissja.katanine.impl.PriceImpl;

import java.util.*;
import java.util.stream.Collectors;

public class XItemsCostYPricingRule implements PricingRule {

    private Map<String, Map<Integer, Price>> priceCalculationMap;
    private PricingRule nextPricingRule;

    public XItemsCostYPricingRule(Map<String, Map<Integer, Price>> priceCalculationMap, PricingRule nextPricingRule) {
        this.priceCalculationMap = priceCalculationMap;
        this.nextPricingRule = nextPricingRule;
    }

    public XItemsCostYPricingRule(Map<String, Map<Integer, Price>> priceCalculationMap) {
        this.priceCalculationMap = priceCalculationMap;
        this.nextPricingRule = new DefaultPricingRule();
    }

    @Override
    public Price calculatePrice(Collection<Item> itemsToCalculate, Price basePrice) {
        Map<String, List<Item>> itemsByIdentifier = itemsToCalculate.stream().collect(Collectors.groupingBy(Item::getIdentifier));
        List<Item> uncalculatedItems = new ArrayList<>();

        itemsByIdentifier.forEach((identifier, items) -> {
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
                PriceImpl calculatedPrice = new PriceImpl(pricePerKeyOccurance);
                calculatedPrice.multiply(numberOfTimesThisKeyCanBeApplied);
                basePrice.add(calculatedPrice);

                numberOfItems -= numberOfTimesThisKeyCanBeApplied * priceMapKey;
            }

            uncalculatedItems.addAll(items.subList(0, numberOfItems));
        });

        return nextPricingRule.calculatePrice(uncalculatedItems, basePrice);
    }
}
