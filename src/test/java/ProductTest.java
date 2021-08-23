import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ProductTest {

    private final double PRICE = 10.0;
    private static final double BOOK_IMPORT_DUTY = 5.5;
    private static final double FOOD_IMPORT_DUTY = 6.0;
    private static final double MEDICINE_IMPORT_DUTY = 4.0;
    private static final double BAG_IMPORT_DUTY = 8.0;

    private static final double SHOE_TAX = 11.0;
    private static final double BAG_TAX = 14.0;
    private static final double TOY_TAX = 18.0;

    private Product book =  new Product("Book1 ", PRICE, false, "Book");
    private Product food = new Product("Chocolate", PRICE, false, "Food");
    private Product medicine = new Product("Medicine1", PRICE, false, "Medicine");
    private Product shoes = new Product("Shoes", PRICE, false, "Shoes");

    private Product bookImported = new Product("Book1 ", PRICE, true, "Book");
    private Product foodImported = new Product("Chocolate", PRICE, true, "Food");
    private Product medicineImported = new Product("Medicine1", PRICE, true, "Medicine");
    private Product shoesImported = new Product("Shoes", PRICE, true, "Shoes");
    private Product bagImported = new Product("Bag", PRICE, true, "Bag");
    private static Map<String, Double> importProductDutyMap = new HashMap<String, Double>();
    private static Map<String, Double> productTaxMap = new HashMap<String, Double>();

    @BeforeAll
    public static void init() {
        importProductDutyMap.put("Book", BOOK_IMPORT_DUTY);
        importProductDutyMap.put("Food", FOOD_IMPORT_DUTY);
        importProductDutyMap.put("Medicine", MEDICINE_IMPORT_DUTY);
        importProductDutyMap.put("Bag", BAG_IMPORT_DUTY);

        productTaxMap.put("Shoes", SHOE_TAX);
        productTaxMap.put("Bag", BAG_TAX);
        productTaxMap.put("Toy", TOY_TAX);
    }

    @Test
    public void importedDutyShouldBeZeroForNonImportedProduct(){
        Assertions.assertEquals(0,book.getProductImportDuty(importProductDutyMap));
    }
    @Test
    public void importDutyOfProductWhenImportProductMapIsProvided() {
        Assertions.assertEquals(BOOK_IMPORT_DUTY* PRICE * 0.01, bookImported.getProductImportDuty(importProductDutyMap));
        Assertions.assertEquals(FOOD_IMPORT_DUTY*PRICE * 0.01, foodImported.getProductImportDuty(importProductDutyMap));
        Assertions.assertEquals(MEDICINE_IMPORT_DUTY * PRICE * 0.01, medicineImported.getProductImportDuty(importProductDutyMap));

    }

    @Test
    public void defaultImportDutyOfProductWhenProductIsNotPresentInImportProductMap() {
        Assertions.assertEquals(Product.DEFAULT_IMPORT_DUTY * PRICE * 0.01, shoesImported.getProductImportDuty(importProductDutyMap));
    }

    @Test
    public void productBasicTaxWhenProductIsNotExempted() {
        Assertions.assertEquals(SHOE_TAX * PRICE * 0.01, shoes.getBasicTaxOnProduct(productTaxMap));
    }

    @Test
    public void productBasicTaxNotApplicableWhenProductIsExempted() {
        Assertions.assertEquals(0 * PRICE * 0.01, book.getBasicTaxOnProduct(productTaxMap));
    }

    @Test
    public void getBagPriceWithApplicableTaxAndImportDuty() {
        Double bagImportDuty = bagImported.getProductImportDuty(importProductDutyMap);
        Double bagTax = bagImported.getBasicTaxOnProduct(productTaxMap);
        Double price =  bagImported.calculatePrice(importProductDutyMap,productTaxMap);
        Assertions.assertEquals(BAG_TAX * PRICE * 0.01, bagTax);
        Assertions.assertEquals(BAG_IMPORT_DUTY * PRICE * 0.01, bagImportDuty);
        Assertions.assertEquals(PRICE + bagImportDuty + bagTax,price);

    }

    @Test
    public void getImportedFoodPrice() {
        Double foodImportDuty = foodImported.getProductImportDuty(importProductDutyMap);
        Double foodTax = foodImported.getBasicTaxOnProduct(productTaxMap);
        Double price =  foodImported.calculatePrice(importProductDutyMap,productTaxMap);
        Assertions.assertEquals(0, foodTax);
        Assertions.assertEquals(FOOD_IMPORT_DUTY * PRICE * 0.01, foodImportDuty);
        Assertions.assertEquals(PRICE + foodImportDuty + foodTax,price);

    }
}
