/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvdrent;

import java.awt.List;
import models.ClientModel;
import models.DvdModel;

/**
 *
 * @author krutulj
 */
public interface DBHelperInterface {
    /***
     * Adds new client to database if it's not already exists
     * @param client that will be add to database
     * @return id of existing or new client
     */
    int addClient(ClientModel client);
    boolean removeClient(int clientId);
    
    /**
     * Adds new dvd to database
     * @param dvd
     * @return 
     */
    int addDVD(DvdModel dvd);
    boolean removeDVD();
    
    List listClients();
    List listDvds();
    List listRentedDvds();
    List listClientWhoRentSomething();
    
    ClientModel findClient(int cliendId);
    DvdModel findDvd(int dvdId);    
    
    boolean rentDvd(int clientId, int dvdId);
    boolean returnDvd(int dvdId);
    
}
