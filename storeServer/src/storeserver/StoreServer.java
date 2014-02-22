/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeserver;

import istore.IStore;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import transaction.Invoice;
import transaction.Transaction;

/**
 *
 * @author anthony
 */
public class StoreServer extends UnicastRemoteObject implements IStore {

    private final Store store;
    public StoreServer(Store store) throws RemoteException {
        super();
        this.store = store;
        
    }
    
    /**
     * for calling with a gui. manager opens gui
     * to start new store
     * @param store 
     */
    public static void startServer(Store store) {
        
    }

    @Override
    public boolean processInvoice(Invoice invoice) throws RemoteException {
        Transaction transaction = invoice.getTransaction();
        try {
            Post post = new Post(this.store, transaction.getTransHeader().getCustomerName());
            post.processTransaction(transaction);
        } catch (IOException ex) {
            Logger.getLogger(StoreServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    public static void main(String args[]) throws IOException {
        try{

            System.out.println("building a new store"); 
            Store store = new Store();
        
            System.out.println("opening store with productCatalog.txt"
                + ", manager \"Anthony\", and store name \"Ziga\"\n");
                store.open("productCatalog.txt", "Anthony", "Ziga");
        
            Registry reg = LocateRegistry.createRegistry(1098);
            reg.rebind("server", new StoreServer(store));
            System.out.println("Server started");

//              System.out.println("\n\nClosing store.....");
//            store.close();

        } catch (RemoteException e) {
            System.out.println(e);
        }
    }

    @Override
    public HashMap getProductCatalog() throws RemoteException {
        return store.getProductCatalog();
    }

    @Override
    public String getStoreName() throws RemoteException {
        return store.getName();
    }
    
}
