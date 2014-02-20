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
import post.Store;

/**
 *
 * @author anthony
 */
public class StoreServer extends UnicastRemoteObject implements IStore {

    public StoreServer() throws RemoteException {
        super();
        
    }

    @Override
    public HashMap getGroductCatalog() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean processInvoice(transaction.Invoice invoice) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void main(String args[]) throws IOException {
        try{
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("building a new store"); 
            Store store = new Store();
        
            System.out.println("opening store with productCatalog.txt"
                + ", manager \"Anthony\", and store name \"Ziga\"\n");
                    store.open("productCatalog.txt", "Anthony", "Ziga");
        
        
//            System.out.println("\n\nClosing store.....");
//            store.close();
            reg.rebind("server", new StoreServer());
            System.out.println("Server started");
        } catch (RemoteException e) {
            System.out.println(e);
        }
    }
    
}
