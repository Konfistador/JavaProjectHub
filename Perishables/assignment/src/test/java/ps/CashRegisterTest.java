package ps;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

import net.bytebuddy.asm.Advice;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * CashRegister is the business class to test. It gets bar code scanner input,
 * is able to output to the ui, and uses the SalesService.
 *
 * @author Pieter van den Hombergh / Richard van den Ham
 */
@ExtendWith(MockitoExtension.class)
public class CashRegisterTest {

    Product lamp = new Product("led lamp", "Led Lamp", 250, 1_234, false);
    Product banana = new Product("banana", "Bananas Fyffes", 100, 9_234, true);
    Product cheese = new Product("cheese", "Gouda 48+", 800, 7_687, true);
    Clock clock = Clock.systemDefaultZone();

    Map<String, Product> products = Map.of(
            "lamp", lamp,
            "banana", banana,
            "cheese", cheese
    );

    @Mock
    Printer printer;

    @Mock
    SalesService salesService;

    @Mock
    UI ui;

    @Captor
    private ArgumentCaptor<SalesRecord> salesRecordCaptor;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Captor
    private ArgumentCaptor<String> stringLineCaptor;

    CashRegister cashRegister;

    @BeforeEach
    void setup() {
        cashRegister = new CashRegister(clock, printer, ui, salesService);
    }

    /**
     * Test that after a scan, a non perishable product is looked up and
     * correctly displayed.Have a look at requirements in the JavaDoc of the
     * CashRegister methods. Test product is non-perishable, e.g. led lamp.
     * <ul>
     * <li>Train the mocked salesService and check if a lookup has been
     * done.<li>Check if the mocked UI was asked to display the
     * product.<li>Ensure that ui.displayCalendar is not called.<b>NOTE
     *
     * @throws ps.UnknownProductException
     */
    @Test
    public void lookupandDisplayNonPerishableProduct() throws UnknownProductException {
        productCaptor = ArgumentCaptor.forClass(Product.class);


        Mockito.when(salesService.lookupProduct(lamp.getBarcode())).thenReturn(lamp);
        cashRegister.scan(lamp.getBarcode());
        Mockito.verify(ui).displayProduct(productCaptor.capture());

        assertThat(productCaptor.getAllValues())
                .as("Expecting the proper product to be displayed via the UI")
                .isNotEmpty()
                .contains(lamp);
        // fail("method lookupandDisplayNonPerishableProduct reached end. You know what to do.");
    }

    /**
     * Test that both the product and calendar are displayed when a perishable
     * product is scanned.
     *
     * @throws UnknownProductException but don't worry about it, since you test
     *                                 with an existing product now.
     */
    @Test
    public void lookupandDisplayPerishableProduct() throws UnknownProductException {
        productCaptor = ArgumentCaptor.forClass(Product.class);

        when(salesService.lookupProduct(banana.getBarcode())).thenReturn(banana);
        cashRegister.scan(banana.getBarcode());
        Mockito.verify(ui).displayCalendar();
        Mockito.verify(ui).displayProduct(productCaptor.capture());

        assertThat(productCaptor.getAllValues())
                .as("Expecting the proper products are being displayed")
                .hasSize(1)
                .contains(banana);

        // fail("method lookupandDisplayPerishableProduct reached end. You know what to do.");
    }

    /**
     * Scan a product, finalize the sales transaction, then verify that the
     * correct salesRecord is sent to the SalesService. Use a non-perishable
     * product. SalesRecord has no equals method (and do not add it), instead
     * use {@code assertThat(...).usingRecursiveComparison().isEqualTo(...)}.
     * Also verify that if you print a receipt after finalizing, there is no output.
     *
     * @throws ps.UnknownProductException
     */
    @Test
    public void finalizeSalesTransaction() throws UnknownProductException {
        //  salesRecordCaptor = ArgumentCaptor.forClass(SalesRecord.class);
        var expectedRecord = new SalesRecord(lamp.getBarcode(), LocalDate.now(clock), lamp.getPrice());

        when(salesService.lookupProduct(lamp.getBarcode())).thenReturn(lamp);
        cashRegister.scan(lamp.getBarcode());
        cashRegister.finalizeSalesTransaction();
        Mockito.verify(salesService).sold(salesRecordCaptor.capture());

        assertThat(salesRecordCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedRecord);

        //  fail("method finalizeSalesTransaction reached end. You know what to do.");
    }

    /**
     * Verify price reductions. For a perishable product with: 10 days till
     * best-before, no reduction; 2 days till best-before, no reduction; 1 day
     * till best-before, 35% price reduction; 0 days till best-before (so sales
     * date is best-before date), 65% price reduction; -1 days till best-before
     * (product over date), 100% price reduction.
     * <p>
     * Check the correct price using the salesService and an argument captor.
     */
    @ParameterizedTest
    @CsvSource({
            "banana,10,100",
            "banana,2,100",
            "banana,1,65",
            "banana,0,35",
            "banana,-1,0",})
    public void priceReductionNearBestBefore(String productName, int daysBest, int expectedPrice) throws UnknownProductException, UnknownBestBeforeException {
        salesRecordCaptor = ArgumentCaptor.forClass(SalesRecord.class);

        when(salesService.lookupProduct(banana.getBarcode())).thenReturn(banana);
        cashRegister.scan(banana.getBarcode());
        cashRegister.correctSalesPrice(LocalDate.now(clock).plusDays(daysBest));
        cashRegister.finalizeSalesTransaction();

        Mockito.verify(salesService).sold(salesRecordCaptor.capture());

        assertThat(salesRecordCaptor.getValue().getSalesPrice())
                .as("Expecting a proper price reduction")
                .isEqualTo(expectedPrice);


        //fail("method priceReductionNearBestBefore reached end. You know what to do.");
    }

    /**
     * When multiple products are scanned, the resulting lines on the receipt
     * should be perishable first, not perishables last. Scan a banana, led lamp
     * and a cheese. The products should appear on the printed receipt in
     * banana, cheese, lamp order. The printed product line on the receipt
     * should contain description, (reduced) salesprice per piece and the
     * quantity.
     */
    @Test
    public void printInProperOrder() throws UnknownBestBeforeException, UnknownProductException {
        stringLineCaptor = ArgumentCaptor.forClass(String.class);
        var expectedString =
                         banana.getDescription() + "\t" + banana.getPrice() + "\t" + 1 + "\t" +
                        cheese.getDescription() + "\t" + cheese.getPrice() + "\t" + 1 + "\t" +
                        lamp.getDescription() + "\t" + lamp.getPrice() + "\t" + 1;

        when(salesService.lookupProduct(lamp.getBarcode())).thenReturn(lamp);
        when(salesService.lookupProduct(banana.getBarcode())).thenReturn(banana);
        when(salesService.lookupProduct(cheese.getBarcode())).thenReturn(cheese);

        cashRegister.scan(lamp.getBarcode());
        cashRegister.scan(banana.getBarcode());
        cashRegister.correctSalesPrice(LocalDate.now(clock).plusDays(5));
        cashRegister.scan(cheese.getBarcode());
        cashRegister.correctSalesPrice(LocalDate.now(clock).plusDays(5));

        cashRegister.printReceipt();

        Mockito.verify(printer, times(3)).println(stringLineCaptor.capture());

        assertThat(String.join("\t", stringLineCaptor.getAllValues()))
                .as("Expecting a proper receipt")
                .isEqualTo(expectedString);
        //fail("method printInProperOrder reached end. You know what to do.");
    }

    /**
     * Test that invoking correctSalesPrice with null parameter results in
     * exception.
     *
     * @throws UnknownProductException (but that one is irrelevant). First scan
     *                                 (scan) a perishable product. Afterwards invoke correctSalesPrice with
     *                                 null parameter. An UnknownProductException should be thrown.
     */
    @Test
    public void correctSalesPriceWithBestBeforeIsNullThrowsException() throws UnknownProductException, UnknownBestBeforeException {

        when(salesService.lookupProduct(banana.getBarcode())).thenReturn(banana);

        cashRegister.scan(banana.getBarcode());
        assertThatThrownBy(() -> cashRegister.correctSalesPrice(null))
                .as("Expecting proper exception when passing a null argument as a BB Date")
                .isExactlyInstanceOf(UnknownBestBeforeException.class)
                .hasMessageContaining("BB Date cannot be null");

        // fail("method correctSalesPriceWithBestBeforeIsNull reached end. You know what to do.");
    }

    /**
     * Test scanning an unknown product results in error message on GUI.
     */
    @Test
    public void lookupUnknownProductShouldDisplayErrorMessage() throws UnknownProductException {
        stringLineCaptor = ArgumentCaptor.forClass(String.class);

        when(salesService.lookupProduct(11)).thenThrow(new UnknownProductException("Product not found"));

        cashRegister.scan(11);

        Mockito.verify(ui).displayErrorMessage(stringLineCaptor.capture());
        assertThat(stringLineCaptor.getValue())
                .as("Expecting a proper error message, when product is not found")
                .isEqualTo("Product not found");

        //fail("method lookupUnknownProduct... reached end. You know what to do.");
    }

    /**
     * Test that a product that is scanned twice, is registered in the
     * salesService with the proper quantity AND make sure printer prints the
     * proper quantity as well.
     *
     * @throws UnknownProductException
     */
    @Test
    public void scanProductTwiceShouldIncreaseQuantity() throws UnknownProductException {
        salesRecordCaptor = ArgumentCaptor.forClass(SalesRecord.class);

        when(salesService.lookupProduct(lamp.getBarcode())).thenReturn(lamp);

        cashRegister.scan(lamp.getBarcode());
        cashRegister.scan(lamp.getBarcode());
        cashRegister.finalizeSalesTransaction();

        Mockito.verify(salesService).sold(salesRecordCaptor.capture());

        assertThat(salesRecordCaptor.getValue().getQuantity())
                .as("Expecting a sales record with proper quantity")
                .isEqualTo(2);
        //fail("method scanProductTwice reached end. You know what to do.");
    }
}
