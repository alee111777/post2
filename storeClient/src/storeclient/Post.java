/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package storeclient;

import storeserver.Store;
import transaction.*;
import java.util.ArrayList;
import java.io.IOException;
import payment.Payment;
import storeserver.Store;

/**
 * Post terminal processes transactions in a file
 * or single transactions at a time.
 * @author anthony
 */
public class Post {
    
    /**
     * process individual transactions
     * @param transaction
     * @return Invoice for the transaction
     */
    public Invoice processTransaction(Transaction transaction) {
        Invoice invoice = new Invoice(transaction);
        transaction.getPayment().processPayment();
        return invoice;
    }
}