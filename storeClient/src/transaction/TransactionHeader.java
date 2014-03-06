/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package transaction;

import java.io.Serializable;

/**
 *  holds header for transactions
 * @author Team Ziga
 */
public class TransactionHeader implements Serializable {
    private String storeName;
    private String customerName;
    
    public TransactionHeader(String storeName, String customerName) {
        this.storeName = storeName;
        this.customerName = customerName;
    }
    
    public String getStoreName() {
        return this.storeName;
    }
    
    public String getCustomerName() {
        return this.customerName;
    }
    
}
