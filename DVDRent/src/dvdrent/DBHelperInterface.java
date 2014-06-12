/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvdrent;


import java.util.List;

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
    Integer addClient(ClientModel client);
    boolean removeClient(int clientId);
    
    
    /**
     * Adds new dvd to database
     * @param dvd
     * @return 
     */
    Integer addDVD(DvdModel dvd);
    boolean removeDVD(int dvdId);
    Integer updateDVD(DvdModel dvd);
    
    List listClients();
    List listDvds();
    List listRentedDvds();
    List listClientWhoRentSomething();
    
    

    ClientModel findClient(int clientId);
    DvdModel findDvd(int dvdId);    
    
    boolean rentDvd(int clientId, int dvdId);
    boolean returnDvd(int dvdId);
    
}
