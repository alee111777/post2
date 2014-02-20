/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package payment;

import java.util.ArrayList;

/**
 * This is the abstract class for all payment types
 * @author Team Ziga
 */
public abstract class Payment {
    
    /**
     * initialize variables in payment
     * @param params ArrayList<String>
     * @return true if success
     */
    public abstract boolean init(ArrayList<String> params);
    
    /**
     * process this payment
     * @return double amount paid
     */
    public abstract double processPayment();  
}
