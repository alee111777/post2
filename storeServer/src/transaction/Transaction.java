/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package transaction;

import java.util.ArrayList;
import payment.Payment;


/**
 * Transactions for the store
 * @author Team Ziga
 */
public class Transaction {
    private TransactionHeader header;
    private ArrayList<TransactionItem> transItems;
    private int numTransItems;
    private Payment payment;
    
    public Transaction() {
        transItems = new ArrayList<TransactionItem>();
    }
    
    /**
     * create a new transaction
     * @param header
     * @param transItems
     * @param numItems
     * @param payment
     * @param customerName 
     */
    public Transaction(TransactionHeader header, 
            ArrayList<TransactionItem> transItems, int numItems, 
            Payment payment, String customerName) {
        this.transItems = transItems;
        this.numTransItems = numItems;
        this.payment = payment;
        this.header = header;
    }
    
    public void setTransHeader(TransactionHeader header) {
        this.header = header;
    }
    
    public void addTransItem(TransactionItem transItem) {
        this.transItems.add(transItem);
    }
    
    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    
    public TransactionHeader getTransHeader() {
        return header;
    }
    
    public ArrayList<TransactionItem> getTransItems() {
        return transItems;
    }
    
    public int getNumTransItems() {
        return numTransItems;
    }
    
    public Payment getPayment() {
        return payment;
    }
    
    /**
     * calculate the total for this transaction
     * @return double = the total
     */
    public double getTotal() {
        double total = 0.0;
        for(int i = 0; i < transItems.size(); i++) {
            total += transItems.get(i).getExtendedPrice();
        }
        return total;
    }
    
    
}
