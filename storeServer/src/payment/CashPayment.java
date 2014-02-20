/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package payment;

import java.util.ArrayList;

/**
 *  this class represents a cash payment
 * @author Team Ziga
 */
public class CashPayment extends Payment {
    private String customerName;
    private double amt;
    
    /**
     * process this payment
     * @return double amount that has been paid
     */
    @Override
    public double processPayment() {
        return amt;
    }
    
    /**
     * initialize CashPayment.
     * given a 2 item array list of strings.
     * first string should be customer name
     * second string should be amount of payment.
     * @param params ArrayList<String>
     * @return boolean true means success
     */
    @Override
    public boolean init(ArrayList<String> params) {
        customerName = params.get(0);
        amt = Double.parseDouble(params.get(1));
        return true;
    }
    
    public double getAmt() {
        return this.amt;
    }
    
    @Override
    public String toString() {
        String invStr = String.format("Amount Tendered: $%.2f", amt);
        return invStr;
    }
}
