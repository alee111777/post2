/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package post;
import transaction.Invoice;
import transaction.Transaction;
import product.ProductSpec;
import product.ProductReader;
import java.io.IOException;
import java.util.*;
import payment.Payment;

/**
 * The store is opened by managers
 * the store contains a catalog of items that can be purchased
 * for which transactions and records are created
 * @author Team Ziga
 */
public class Store {
    
    //Products by UPC code
    private static HashMap<String,ProductSpec> productCatalog = new HashMap();
    private ArrayList<Transaction> dailyTransactions;
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
    
    /**
     * close the store
     */
    public void close() {
        
    }
    
    /**
     * given a file name and customer name, the transactions in the file
     * will be processed.
     * @param fileName String
     * @param customerName String
     * @return ArrayList<Invoices> invoices for the transactions
     * @throws IOException 
     */
    public ArrayList<Invoice> processTransactionFile(String fileName, String customerName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Post post = new Post(this, customerName);
        return post.processTransactionFile(fileName);
    }
    
    /**
     * add a transaction to the store's record of daily transactions
     * @param transaction
     * @return true if success
     */
    public boolean addDailyTransaction(Transaction transaction) {
        dailyTransactions.add(transaction);
        return true;
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
   
   
//    /**
//     * get product price by upc code
//     * @param upc
//     * @return double the rice
//     */
//    public double getProductPrice(String upc) {
//        ProductSpec product = productCatalog.get(upc);
//        return product.getPrice();
//    }
//    
//    /**
//     * get product description by upc code
//     * @param upc
//     * @return String description
//     */
//    public String getProductDescription(String upc) {
//        ProductSpec product = productCatalog.get(upc);
//        return product.getDescription();
//    }
    
    /**
     * get store name
     * @return String
     */
    public String getName() {
        return this.storeName;
    }
    
}
