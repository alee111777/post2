/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package storeserver;
import transaction.Invoice;
import product.ProductSpec;
import product.ProductReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import payment.Payment;

/**
 * The store is opened by managers
 * the store contains a catalog of items that can be purchased
 * for which transactions and records are created
 * @author Team Ziga
 */
public class Store implements Serializable {
    
    //Products by UPC code
    private HashMap<String,ProductSpec> productCatalog = new HashMap();
    private Double dailyTotalPayments;
    private String storeName;
    private ArrayList<Invoice> dailyInvoices;
    private ArrayList<Payment> payments;
    
    /**
     * open the store with a catalog, manager, and store name
     * @param catalogueFileName String
     * @param managerName String
     * @param storeName String
     * @throws IOException 
     */
    public void open(String catalogueFileName, String managerName, String storeName) throws IOException {
        dailyTotalPayments = 0.0;
        productCatalog = ProductReader.getCatalog(catalogueFileName);
        this.storeName = storeName;
        dailyInvoices = new ArrayList<Invoice>();
        payments = new ArrayList<Payment>();
    }
   
    public HashMap getProductCatalog() {
        return productCatalog;
    }
    
    /**
     * close the store
     */
    public void close() {
        
    }
    
    /**
     * add the daily total payments for the store
     * @param amount double
     * @return Double the total daily payments for the store
     */
    public Double addToDailyTotalPayments(Double amount) {
        this.dailyTotalPayments += amount;
        return this.dailyTotalPayments;
    }
    
    /**
     * add to daily invoice list
     * @param invoice
     * @return true if success
     */
    public boolean addDailyInvoice(Invoice invoice) {
        dailyInvoices.add(invoice);
        return true;
    }
  
   public ProductSpec getProductSpec(String upc) {
       return productCatalog.get(upc);
   }
   
   
   public String[] getUPCList() {
       Set<String> upcSet = this.productCatalog.keySet();
       String[] upcList = new String[upcSet.size()];
       return upcList;
   }
   
    /**
     * get store name
     * @return String
     */
    public String getName() {
        return this.storeName;
    }
    
}
