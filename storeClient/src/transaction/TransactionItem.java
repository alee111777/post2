/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package transaction;

import java.io.Serializable;

/**
 *  items within a transaction
 * @author Team Ziga
 */
public class TransactionItem implements Serializable {
    
    private int quantity;
    private String upc;
    private double unitPrice;
    private double extendedPrice;
    private String productName;
    
    public TransactionItem(int numProduct, String productCode, String productName, double productCost) {
        quantity = numProduct;
        upc = productCode;
        unitPrice = productCost;
        extendedPrice = productCost * quantity;
        this.productName = productName;
        
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public String getUPC() {
        return upc;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double getExtendedPrice() {
        return extendedPrice;
    }
    
    public String getName() {
        return productName;
    }
}
