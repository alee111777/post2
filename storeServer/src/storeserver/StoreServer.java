

package storeserver;

import istore.IStore;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import transaction.Invoice;

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

    @Override
    public boolean processInvoice(Invoice invoice) throws RemoteException {
        store.addDailyInvoice(invoice);
        
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
            
            System.out.println("press enter to shut down the server.");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            System.out.println("\n\nClosing store.....");
            store.close();
            System.exit(0);

        } catch (RemoteException e) {
            System.out.println(e);
        }
    }


    
    @Override
    public Store getStore() {
       return this.store;
    }
   
}
