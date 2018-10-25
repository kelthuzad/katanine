package de.kneissja.katanine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length == 0) {
            logger.error("Please pass the items as parameter e.g. AABA");
            return;
        }

        Path itemPricesFile = Paths.get(ClassLoader.getSystemResource("itemprices.csv").toURI());
        ItemService inventory = initInventory(itemPricesFile);
        List<ItemIdentifier> itemIdentifiers = parseInput(args[0]);
        List<Item> items = itemIdentifiers.stream().map(inventory::findItem).collect(Collectors.toList());

        Path ruleFile = Paths.get(ClassLoader.getSystemResource("xitemscostyrules.csv").toURI());
        Map<ItemIdentifier, Map<Integer, Price>> xItemsCostYRules = loadXItemsCostYRules(ruleFile);

        PricingRule pricingRule = new XItemsCostYPricingRule(xItemsCostYRules)
                .setNextPricingRule(new SimplePricingRule());


        Checkout checkout = new CheckoutFactory().createCheckout(pricingRule);
        items.forEach(checkout::scan);

        System.out.println(checkout.getTotal().getPriceValue());
    }

    private static List<ItemIdentifier> parseInput(final String input) {
        String[] itemTokens = input.split("");
        return Arrays.stream(itemTokens)
                .map(ItemIdentifier::valueOf)
                .collect(Collectors.toList());
    }

    private static ItemService initInventory(Path itemFile) throws IOException {
        List<List<String>> itemData = new CSVLoader().loadFile(itemFile);
        ItemService inventory = new ItemService();

        itemData.forEach(itemInfos -> {
            if (itemInfos.size() != 2) {
                logger.error("Invalid data read from file {}: {}", itemFile, itemInfos);
                return; // skip this item
            }
            ItemIdentifier identifier = ItemIdentifier.valueOf(itemInfos.get(0));
            Price price = new Price(Integer.parseInt(itemInfos.get(1)));
            inventory.addItem(identifier, price);
        });
        return inventory;
    }

    private static Map<ItemIdentifier, Map<Integer, Price>> loadXItemsCostYRules(Path ruleFile) throws IOException {
        List<List<String>> ruleData = new CSVLoader().loadFile(ruleFile);
        Map<ItemIdentifier, Map<Integer, Price>> rules = new HashMap<>();

        ruleData.forEach(ruleInfo -> {
            if (ruleInfo.size() != 3) {
                logger.error("Invalid data read from file {}: {}", ruleFile, ruleInfo);
                return; // skip this item
            }
            ItemIdentifier identifier = ItemIdentifier.valueOf(ruleInfo.get(0));
            Integer amount = Integer.parseInt(ruleInfo.get(1));
            Price price = new Price(Integer.parseInt(ruleInfo.get(2)));

            // create map entry if not yet present
            Map<Integer, Price> itemPriceMap = rules.computeIfAbsent(identifier, k -> new HashMap<>());
            itemPriceMap.put(amount, price);
        });
        return rules;
    } */
}
