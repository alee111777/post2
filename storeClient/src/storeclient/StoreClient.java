/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeclient;

import istore.IStore;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import postGUI.PostGUI;
import storeserver.Store;

/**
 *
 * @author anthony
 */
public class StoreClient {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StoreClient post = new StoreClient();
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1098);
            IStore storeServer = (IStore)reg.lookup("server");
            PostGUI.createAndShow(storeServer);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
     
    }
    
    /**
     * open connection to store and get catalog
     * @return 
     */
    private Store getServerStore() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1098);
            IStore storeServer = (IStore)reg.lookup("server");
            return storeServer.getStore();
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    
    
}
