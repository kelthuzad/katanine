package de.kneissja.katanine.pricingrule.rules;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.kneissja.katanine.item.Item;
import de.kneissja.katanine.pricingrule.PricingRule;
import de.kneissja.katanine.price.Price;
import de.kneissja.katanine.item.ItemIdentifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Pricing rule that calculates special prices for items with multiple occurance.
 * The special pricing can be defined by the priceCalculationMap in the constructor.
 */
public class XItemsCostYPricingRule implements PricingRule {

    @JsonProperty
    private Map<ItemIdentifier, Map<Integer, Price>> priceCalculationMap;

    @JsonProperty
    private PricingRule nextPricingRule;

    /**
     * Creates a new pricing rule instance
     * @param priceCalculationMap map with the info about the special price. The map must contain one entry per item identifier.
     *                            Each entry contains itself a mapping from "number of items" to "special price for this number of items"
     */
    public XItemsCostYPricingRule(Map<ItemIdentifier, Map<Integer, Price>> priceCalculationMap) {
        this.priceCalculationMap = priceCalculationMap;
    }

    /**
     * Default constructor for deserialization
     */
    public XItemsCostYPricingRule() {

    }

    @Override
    public PricingRule setNextPricingRule(PricingRule nextPricingRule) {
        this.nextPricingRule = nextPricingRule;
        return this;
    }

    @Override
    public Price calculatePrice(Collection<Item> itemsToCalculate, Price basePrice) {
        Map<ItemIdentifier, List<Item>> itemsByIdentifier = itemsToCalculate.stream().collect(Collectors.groupingBy(Item::getIdentifier));

        List<Item> uncalculatedItems = new ArrayList<>(); // items that cannot be calculated by this rule
        Price calculatedPrice = basePrice;

        for (Map.Entry<ItemIdentifier, List<Item>> entry: itemsByIdentifier.entrySet()) {
            calculatedPrice = calculateItemPrice(uncalculatedItems, calculatedPrice, entry.getKey(), entry.getValue());
        }

        if (!uncalculatedItems.isEmpty() && nextPricingRule == null) {
            throw new IllegalStateException("There are uncalculated items and there is no next pricing rule. Cannot calculate the price, please set a next pricing rule");
        }

        return nextPricingRule.calculatePrice(uncalculatedItems, calculatedPrice);
    }

    private Price calculateItemPrice(List<Item> uncalculatedItems, Price currentPrice, ItemIdentifier identifier, List<Item> items) {
        Price newPrice = currentPrice;

        if (!priceCalculationMap.containsKey(identifier)) {
            // there is no rule for this kind of item, price cannot be calculated here
            uncalculatedItems.addAll(items);
            return newPrice;
        }

        Map<Integer, Price> specialPriceMap = priceCalculationMap.get(identifier); //  map with item-amount -> special-price
        List<Integer> sorteditemAmountDefinitionList = specialPriceMap.keySet().stream() // sort item-amount definitions descending
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        int numberOfItems = items.size();
        for (Integer itemAmountDefinition : sorteditemAmountDefinitionList) {
            int numberOfTimesThisItemAmountCanBeApplied = numberOfItems / itemAmountDefinition;

            if (numberOfTimesThisItemAmountCanBeApplied == 0) {
                continue;
            }

            Price specialPriceForThisAmountOfItems = specialPriceMap.get(itemAmountDefinition);
            Price calculatedPrice = specialPriceForThisAmountOfItems.multiply(numberOfTimesThisItemAmountCanBeApplied);
            newPrice = newPrice.add(calculatedPrice);
            numberOfItems -= numberOfTimesThisItemAmountCanBeApplied * itemAmountDefinition;

            if (numberOfItems == 0) {
                break; // all items were calculated
            }
        }

        uncalculatedItems.addAll(items.subList(0, numberOfItems)); // add the amount of items that could not be calculated
        return newPrice;
    }
}
