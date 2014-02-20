package transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import payment.*;
/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

/**
 * Invoice for transactions
 * @author Team Ziga
 */
public class Invoice {
    private double amountTendered;
    private double amountReturned;
    private Transaction transaction;
    private String dateTime;
    
    /**
     * create an invoice given the transaction
     * @param transaction 
     */
    public Invoice(Transaction transaction) {
        this.transaction = transaction;
        Payment payment = transaction.getPayment();
        if (payment instanceof CashPayment) {
            double tendered = ((CashPayment)(payment)).getAmt();
            this.amountReturned = (-1)*(transaction.getTotal() - tendered); 
        } else {
            this.amountReturned = 0.0;
        }
        
        dateTime = this.getDateTime();
    }
    
    
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    @Override
    public String toString() {
        String invoiceString = "Store: " + transaction.getTransHeader().getStoreName() + "\n\n"
                + "Customer Name: " + transaction.getTransHeader().getCustomerName()+ "    " 
                + "getDateTime" + "\n\n";
        
        invoiceString += String.format("%-22s %5s %22s %22s\n",
                "Item", "QTY", "UNIT_PRICE", "EXTENDED_PRICE");
        invoiceString += String.format("%-22s %5s %22s %22s\n",
                "----", "---", "----------", "--------------");
        ArrayList<TransactionItem> transactionItems = transaction.getTransItems();
        for(int i = 0; i < transactionItems.size(); i++) {
            TransactionItem item = transactionItems.get(i);
            String formatItem = String.format("%-22s %5d %22.2f %22.2f\n",
                    item.getName(), item.getQuantity(), item.getUnitPrice(), item.getExtendedPrice());
            invoiceString += formatItem;
        }
        
        invoiceString += "\n-------------------------------\n"
                + String.format("Total: $%.2f", this.transaction.getTotal())
                + "\n" + this.transaction.getPayment().toString()
                + String.format("\nAmount Returned: $%.2f", this.amountReturned);
        return invoiceString;
    }
}
