/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package payment;

import java.util.ArrayList;

/**
 * this class represents a check payment
 * @author Team Ziga
 */
public class CheckPayment extends Payment {
    private String customerName;
    private double amt;
    
    /**
     * initialize check Payment.
     * given a 2 item array list of strings.
     * first string should be customer name
     * second string should be amount of payment.
     * @param params ArrayList<String>
     * @return double the amount that was paid
     */
    @Override
    public boolean init(ArrayList<String> params) {
        customerName = params.get(0);
        amt = Double.parseDouble(params.get(1));
        return true;
    }
    
    /**
     * process this payment
     * @return double amount paid
     */
    @Override
    public double processPayment() {
        return amt;
    }
    
    @Override
    public String toString() {
        String invString = "Amount Tendered: Paid by check";
        return invString;
    }    
}
