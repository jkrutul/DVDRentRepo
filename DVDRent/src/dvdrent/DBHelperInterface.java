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
    Integer addClient(ClientModel client);
    boolean removeClient(Integer clientId);
    
    
    /**
     * Adds new dvd to database
     * @param dvd
     * @return 
     */
    Integer addDVD(DvdModel dvd);
    boolean removeDVD();
    Integer updateDVD(DvdModel dvd);
    
    List listClients();
    List listDvds();
    List listRentedDvds();
    List listClientWhoRentSomething();
    
    

    ClientModel findClient(Integer clientId);
    DvdModel findDvd(Integer dvdId);    
    
    boolean rentDvd(Integer clientId, Integer dvdId);
    boolean returnDvd(Integer dvdId);
    
}
