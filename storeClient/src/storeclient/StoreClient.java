/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package storeclient;

import istore.IStore;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author anthony
 */
public class StoreClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    private void connectServer() {
        try {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
            IStore storeServer = (IStore)reg.lookup("server");
            //String text = storeServer.getData("Anthony");
            //System.out.println(text);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
