/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package post;

import transaction.*;
import java.util.ArrayList;
import java.io.IOException;
import payment.Payment;

/**
 * Post terminal processes transactions in a file
 * or single transactions at a time.
 * @author anthony
 */
public class Post {
    private TransactionReader tReader;
    private Store store;
    private ArrayList<Invoice> invoices;
    private String userName;
    private ArrayList<Payment> payments;
    
    
    
    Post(Store store, String userName) throws IOException {
        this.userName = userName;
        this.store = store;
    }
    
    /**
     * process individual transactions
     * @param transaction
     * @return Invoice for the transaction
     */
    public Invoice processTransaction(Transaction transaction) {
        Invoice invoice = new Invoice(transaction);
        store.addDailyInvoice(invoice);
        store.addToDailyTotalPayments(transaction.getTotal());
        return invoice;
    }
    
    /**
     * process a file of transactions
     * @param fileName
     * @return ArrayList<Invoice> invoices for the transaction
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException 
     */
    public ArrayList<Invoice> processTransactionFile(String fileName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        tReader = new TransactionReader(this.store, fileName);
        invoices = new ArrayList<Invoice>();
        while(tReader.hasMoreTransactions()) {
            Transaction transaction = tReader.getNextTransaction();
            Invoice invoice = this.processTransaction(transaction);
            invoices.add(invoice);
        }
        return invoices;
    }
}