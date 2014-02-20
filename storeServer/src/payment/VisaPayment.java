/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package payment;

import java.util.ArrayList;

/**
 *
 * @author Team Ziga
 */
public class VisaPayment extends Payment {
    private String customerName;
    private int ccNum;
    private double amt;
    
    /**
     * initialize Visa Payment.
     * given a 2 item array list of strings.
     * first string should be customer name
     * second string should be cc number.
     * @param params ArrayList<String>
     * @return boolean true if success
     */
    public boolean init(ArrayList<String> params) {
        customerName = params.get(0);
        ccNum = Integer.parseInt(params.get(1));
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
    
    public String toString() {
        String invString = "Amount Tendered: Paid by Visa";
        return invString;
    }
    
}
