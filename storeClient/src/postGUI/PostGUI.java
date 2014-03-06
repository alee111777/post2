/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package postGUI;

import istore.IStore;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import payment.*;
import product.ProductSpec;
import storeserver.Store;
import transaction.Invoice;
import transaction.Transaction;
import transaction.TransactionHeader;
import transaction.TransactionItem;

/**
 *
 * @author Ye
 */
public class PostGUI extends javax.swing.JFrame {

    private HashMap catalog;
    public Transaction transaction;
    private String storeName;
    private IStore storeServer;
    private ArrayList<Invoice> pendingInvoices;
    private String[] items; //read through rmi
    private ProductChangeListener productPanelListner;
    private PayChangeListener paymentPanelListner;
    
    /**
     * Creates new form PostGUI
     * @param storeServer
     * @throws java.rmi.RemoteException
     */
    public PostGUI(IStore storeServer) throws RemoteException {
        this.catalog = storeServer.getProductCatalog();
        this.storeName = storeServer.getStoreName();
        ArrayList upcList = new ArrayList(catalog.keySet());
        Collections.sort(upcList);
        items = new String[upcList.size()];

        int i = 0;
        for (Object upc : upcList) {
            items[i]=((String)upc);
            i++;
        }
        productPanelListner = new ProductChangeListener();
        paymentPanelListner = new PayChangeListener();
        initComponents();
        transaction = new Transaction();
        pendingInvoices = new ArrayList<Invoice>();
        this.storeServer = storeServer;
    }
    
    public Transaction getTransaction()
    {
        return this.transaction;
    }
    
    public void reset() {
        
        transaction = new Transaction();
        invoicePanel.reset();
        productPanel.reset();
        namePanel.reset();
        paymentPanel.reset();
        this.repaint();
    }
    
    public class ProductChangeListener implements PropertyChangeListener {
	        // This method is called every time the property value is changed
	        public void propertyChange(PropertyChangeEvent evt) {
	            System.out.println("enter button clicked");
                    //add to show in the invoice pannel
                    
                    String upc = productPanel.getSelectedUPC();
                    ProductSpec productSpec = (ProductSpec)catalog.get(upc);
                    TransactionItem transItem = new TransactionItem(Integer.parseInt(productPanel.getQuantity()),
                        upc, productSpec.getDescription(), productSpec.getPrice());

                    String formatItem = String.format("%-22s\t %5d\t %14.2f\t\t %15.2f\n",
                        transItem.getName(), transItem.getQuantity(), transItem.getUnitPrice(), transItem.getExtendedPrice());
                
                    // BRAND NEW
                    invoicePanel.appendTransItems(formatItem);
                    transaction.addTransItem(transItem);

                    // BRAND NEW
                    invoicePanel.setTotalAmount("$" + String.valueOf(transaction.getTotal()) + "0");              
                }
	    }
    public class PayChangeListener implements PropertyChangeListener {
	        // This method is called every time the property value is changed
	public void propertyChange(PropertyChangeEvent evt) {
	            System.out.println("pay button clicked");
                    
                    //add to show in the invoice pannel
                    String customerName = namePanel.getCustomerName();
                    String ccNum = "";
                    ArrayList<String> params = new ArrayList<String>();

                    if (customerName.compareTo("") == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter your name.");
                        return;
                    }
                    if (transaction.getTransItems().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please add some items to pay for first.");
                        return;
                    }
                    
                    String payType = paymentPanel.getPayType();
                    Payment payment = null;
                    try {
                        payment = (Payment)(Class.forName("payment." + payType + "Payment").newInstance());
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    params.add(customerName);
                    if (payment instanceof MastercardPayment
                        || payment instanceof VisaPayment) {
                        ccNum = JOptionPane.showInputDialog(
                            null, "Please enter your credit number", null);

                        params.add(ccNum);
                        payment.init(params);

                    } else if (payment instanceof CheckPayment) {

                        params.add(String.valueOf(transaction.getTotal()));
                        payment.init(params);

                    } else if (payment instanceof CashPayment) {
                        String amount = paymentPanel.getAmount();
                        try {
                            Double.parseDouble(amount);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Please enter a valid amount");
                            return;
                        }
                        params.add(amount);
                        payment.init(params);
                    }

                    transaction.setPayment(payment);
                    TransactionHeader header =
                            new TransactionHeader(storeName, customerName);
                    transaction.setTransHeader(header);

                    Invoice invoice = new Invoice(transaction);
                    try {
                        storeServer.processInvoice(invoice);
                        JOptionPane.showMessageDialog(null, "Payment processed");
                        JTextArea customFontText = new JTextArea();
                        customFontText.setFont (new Font ("MONOSPACED", Font.PLAIN, 13));
                        customFontText.setText(invoice.toString());
                        JOptionPane.showMessageDialog(null, customFontText);
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(null, "\nServer not available. "
                            + "Payment still pending. Will be proccessed later.");
                        pendingInvoices.add(invoice);
                        JTextArea customFontText = new JTextArea();
                        customFontText.setFont (new Font ("MONOSPACED", Font.PLAIN, 13));
                        customFontText.setText(invoice.toString());
                        JOptionPane.showMessageDialog(null, invoice.toString());
                    }
                    reset();                    
	        }
	    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentPanel = new postGUI.PaymentPanel(paymentPanelListner);
        invoicePanel = new postGUI.InvoicePanel();
        productPanel = new postGUI.ProductPanel(items,productPanelListner);
        namePanel = new postGUI.NamePanel();
        timePanel = new postGUI.TimePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POST Terminal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(productPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(timePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(paymentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(invoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(invoicePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(paymentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void createAndShow(final IStore storeServer) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PostGUI(storeServer).setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
    /**
     * use to test gui
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PostGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*try {
                    new PostGUI().setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                System.out.println("building a new store"); 
                Store store = new Store();
                
                System.out.println("opening store with productCatalog.txt"
                        + ", manager \"Anthony\", and store name \"Ziga\"\n");
                try {
                    store.open("productCatalog.txt", "Anthony", "Ziga");
                } catch (IOException ex) {
                    Logger.getLogger(PostGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private postGUI.InvoicePanel invoicePanel;
    private postGUI.NamePanel namePanel;
    private postGUI.PaymentPanel paymentPanel;
    private postGUI.ProductPanel productPanel;
    private postGUI.TimePanel timePanel;
    // End of variables declaration//GEN-END:variables
}
