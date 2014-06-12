/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dvdrent;


import java.util.List;

import org.bson.types.ObjectId;

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
    ObjectId addClient(ClientModel client);
    boolean removeClient(ObjectId clientId);
    
    
    /**
     * Adds new dvd to database
     * @param dvd
     * @return 
     */
    ObjectId addDVD(DvdModel dvd);
    boolean removeDVD(ObjectId dvdId);
    ObjectId updateDVD(DvdModel dvd);
    
    List listClients();
    List listDvds();
    List listRentedDvds();
    List listClientWhoRentSomething();
    
    

    ClientModel findClient(ObjectId clientId);
    DvdModel findDvd(ObjectId dvdId);    
    
    boolean rentDvd(ObjectId clientId, ObjectId dvdId);
    boolean returnDvd(ObjectId dvdId);
    
}
