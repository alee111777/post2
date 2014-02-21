/*
 * CSC 668 SFSU
 * Project POST1
 * Team Ziga
 */

package transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import payment.*;
import storeserver.Store;
import product.ProductSpec;


/**
 * TransactionReader takes a file and creates the transactions
 * in the file
 * @author Team Ziga
 */
public class TransactionReader {
    
    private BufferedReader source;
    private ArrayList<Transaction> transactions;
    private int transactionIndex;

    /**
     * initiate a reader with store name and transaction file,
     * creates a list of transactions that can be accessed
     * @param store
     * @param transactionFile
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public TransactionReader(Store store, String transactionFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        transactionIndex = 0;
        String line;
        transactions = new ArrayList<Transaction>();
        source = new BufferedReader(new FileReader(transactionFile));
        
        //read whole file
        while((line = source.readLine()) != null) {
            //record customer name line
            String customerName = line;
            
            //read in items
            line = source.readLine();
            ArrayList<TransactionItem> itemList = new ArrayList<TransactionItem>();
            StringTokenizer tok = new StringTokenizer(line); 
            String productCode = tok.nextToken();
            while (productCode.matches("[0-9][0-9][0-9][0-9]")) {
                ProductSpec productSpec = store.getProductSpec(productCode);
                String productName = productSpec.getDescription();
                int numProduct = 1;
                double unitPrice = productSpec.getPrice();
                if (tok.hasMoreTokens())
                    numProduct = Integer.parseInt(tok.nextToken());
                
                TransactionItem item = new TransactionItem(
                        numProduct, productCode, productName, unitPrice);
                
                itemList.add(item);
                line = source.readLine();
                tok = new StringTokenizer(line); 
                productCode = tok.nextToken();
            }
            
            //read in payment
            tok = new StringTokenizer(line);
            String pType = tok.nextToken();
            Payment payment = (Payment)(Class.forName("payment." + pType + "Payment").newInstance());
            String str = tok.nextToken();
            ArrayList<String> params = new ArrayList<String>();
            params.add(customerName);
            params.add(str);
            payment.init(params);
            
            //build transaction and add to list
            TransactionHeader header = new TransactionHeader(store.getName(), customerName);
            Transaction trans = new Transaction(header, itemList, itemList.size(), payment, customerName);
            transactions.add(trans);
            
            //go past the empty line
            source.readLine();
            
        }
        source.close();
    }
    
    public boolean hasMoreTransactions() {
        return transactionIndex < (transactions.size());
    }
    
    /**
     * used to test TransactionReader
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Store store = new Store();
        store.open("productCatalog.txt", "Anthony", "Ziga");
        TransactionReader tReader = new TransactionReader(store, "transaction.txt");
        while(tReader.hasMoreTransactions()) {
            Transaction temp = tReader.getNextTransaction();
            System.out.println(temp.getPayment());
        }
        
        store.close();
    }

    public Transaction getNextTransaction() {
        return transactions.get(transactionIndex++);
    }
}