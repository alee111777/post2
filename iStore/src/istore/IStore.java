/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package istore;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import transaction.Invoice;

/**
 *
 * @author anthony
 */
public interface IStore extends Remote {
    public HashMap getGroductCatalog() throws RemoteException;
    public boolean processInvoice(Invoice invoice) throws RemoteException;
}




