package ps;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class to be developed test driven with Mockito.
 *
 * @author Pieter van den Hombergh / Richard van den Ham
 */
class CashRegister {

    private final Clock clock;
    private final Printer printer;
    private final UI ui;
    private final SalesService salesService;

    // Declare a field to keep a salesCache, which is a mapping between a Product and a SalesRecord.
    // When a product gets scanned multiple times, the quantity of the salesRecord is increased. 
    // A LinkedHashMap has the benefit that, in contrast to the HashMap, the order in which 
    // the items were added is preserved.

    // Declare a field to keep track of the last scanned product, initially being null.

    private final Map<Product, SalesRecord> productCache;
    private Product lastScanned = null;

    /**
     * Create a business object
     *
     * @param clock        wall clock
     * @param printer      to use
     * @param ui           to use
     * @param salesService to use
     */
    CashRegister(Clock clock, Printer printer, UI ui, SalesService salesService) {
        this.clock = clock;
        this.printer = printer;
        this.ui = ui;
        this.salesService = salesService;
        this.productCache = new LinkedHashMap<>();
    }

    /**
     * The scan method is triggered by scanning a product by the cashier.
     * Get the product from the salesService. If the product can't be found, an UnknownProductException is thrown and the
     * error message from the exception is shown on the display (ui).
     * If found, check if there is a salesRecord for this product already. If not, create one. If it exists, update the quantity.
     * In case a perishable product was scanned, the cashier should get a calendar on his/her display.
     * The product is displayed on the display.
     *
     * @param barcode
     */
    public void scan(int barcode) {
        Product scanned = null;
        try {
            scanned = salesService.lookupProduct(barcode);
        } catch (UnknownProductException e) {
            ui.displayErrorMessage(e.getMessage());
        }

        if (productCache.containsKey(scanned)) productCache.get(scanned).increaseQuantity(1);
        else if (!Objects.isNull(scanned)) {
            productCache.put(scanned, new SalesRecord(scanned.getBarcode(), LocalDate.now(clock), scanned.getPrice()));
        }


        if (!Objects.isNull(scanned) && scanned.isPerishable()) {
            ui.displayCalendar();
            lastScanned = scanned;
        }
        if (!Objects.isNull(scanned)) ui.displayProduct(scanned);
    }

    /**
     * Submit the sales to the sales service, finalizing the sales transaction.
     * All salesRecords in the salesCache are stored (one-by-one) in the salesService.
     * All caches are reset.
     */
    public void finalizeSalesTransaction() {
        // salesService.sold(productCache.values().stream().for().get());
        for (SalesRecord record : productCache.values()) {
            salesService.sold(record);
        }
        productCache.clear(); // Clearing the cache
    }

    /**
     * Correct the sales price of the last scanned product by considering the
     * given best before date, then submit the product to the service and save
     * in list.
     * <p>
     * This method consults the clock to see if the product is eligible for a
     * price reduction because it is near or at its best before date.
     * <p>
     * Precondition is that the last scanned product is the perishable product.
     * You don't need to check that in your code.
     * <p>
     * To find the number of days from now till the bestBeforeDate, use
     * LocalDate.now(clock).until(bestBeforeDate).getDays();
     * <p>
     * Depending on the number of days, update the price in the salesRecord folowing the
     * pricing strategy as described in the assignment
     * <p>
     * Update the salesRecord belonging to the last scanned product if necessary, so
     * update the price and set the BestBeforeDate.
     *
     * @param bestBeforeDate
     * @throws UnknownProductException in case the best before date is null.
     */
    public void correctSalesPrice(LocalDate bestBeforeDate) throws UnknownBestBeforeException {

        if (Objects.isNull(bestBeforeDate)) throw new UnknownBestBeforeException("BB Date cannot be null");

        var productRecord = productCache.get(lastScanned);
        productRecord.setBestBeforeDate(bestBeforeDate);

        int daysLeft = (int) ChronoUnit.DAYS.between(LocalDate.now(clock), bestBeforeDate);

        if (daysLeft == 1) {
            productRecord.setSalesPrice((int) (productRecord.getSalesPrice() * 0.65));
        } else if (daysLeft == 0) {
            productRecord.setSalesPrice((int) (productRecord.getSalesPrice() * 0.35));
        } else if (daysLeft < 0) {
            productRecord.setSalesPrice(0);
        }

    }

    /**
     * Print the receipt for all the sold products, to hand the receipt to the
     * customer. The receipt contains lines containing: the product description,
     * the (possibly reduced) sales price per piece and the quantity, separated by
     * a tab.
     * The order of printing is the order of scanning, however Perishable
     * products are printed first. The non-perishables afterwards.
     */
    public void printReceipt() {
//        AtomicReference<String> perishables = new AtomicReference<>("");
//        AtomicReference<String> nonperishables = new AtomicReference<>("");
//
//        if (!productCache.isEmpty()) {
//            productCache.forEach((k, v) -> {
//                if (!perishables.toString().equals("")) perishables.set(perishables.get() + "\t");
//
//                if (k.isPerishable()) {
//                   // perishables.set(perishables.get() + k.getDescription() + "\t" + v.getSalesPrice() + "\t" + v.getQuantity());
//                    printer.println(k.getDescription() + "\t" + v.getSalesPrice() + "\t" + v.getQuantity());
//                } else {
//                   // nonperishables.set(perishables.get() + "\t" + k.getDescription() + "\t" + v.getSalesPrice() + "\t" + v.getQuantity());
//                    printer.println();
//                }
//            });
//        }
//        printer.println(perishables + "" + nonperishables);

        if (!productCache.isEmpty()) {
            productCache.forEach((k, v) -> {
                if (k.isPerishable())
                    printer.println(k.getDescription() + "\t" + v.getSalesPrice() + "\t" + v.getQuantity());
            });
            productCache.forEach((k, v) -> {
                if (!k.isPerishable())
                    printer.println(k.getDescription() + "\t" + v.getSalesPrice() + "\t" + v.getQuantity());
            });

        }

    }
}
