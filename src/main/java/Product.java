import java.util.Map;

public class Product {
    private final String productName;
    private final double price;
    private final boolean isImported;
    private final String productType;

    public static final Double DEFAULT_IMPORT_DUTY = 5.0;
    public static final Double DEFAULT_BASIC_TAX_DUTY = 10.0;

    public Product(String productName, double price, boolean isImported, String productType) {
        this.productName = productName;
        this.price = price;
        this.isImported = isImported;
        this.productType = productType;
    }

    public Double getProductImportDuty(Map<String,Double>importedProductMap) {
        double importDuty = 0.0;
        if(isImported){
            importDuty = getImportDutyCharge(importedProductMap);
        }
        return price * importDuty;
    }

    public Double getBasicTaxOnProduct(Map<String,Double>productTaxMapForNonExemptedProduct){
        double tax = 0.0 ;
        if(productTaxMapForNonExemptedProduct != null && productTaxMapForNonExemptedProduct.containsKey(productType)){
            tax = getBasicTaxCharge(productTaxMapForNonExemptedProduct);
        }
        return price * tax;
    }

    private Double getImportDutyCharge (Map<String,Double>productDutyMap){
        if(productDutyMap!=null && productDutyMap.get(productType)!=null){
            return productDutyMap.get(productType) * 0.01;
        }
        return DEFAULT_IMPORT_DUTY * 0.01 ;
    }

    private Double getBasicTaxCharge (Map<String,Double>productDutyMap){
        if(productDutyMap!=null && productDutyMap.get(productType)!=null){
            return productDutyMap.get(productType) * 0.01;
        }
        return DEFAULT_BASIC_TAX_DUTY * 0.01 ;
    }

    public Double calculatePrice(Map<String,Double>importedProductMap , Map<String,Double>productTaxMap){
        return price + getBasicTaxOnProduct(productTaxMap) + getProductImportDuty(importedProductMap);

    }
}
