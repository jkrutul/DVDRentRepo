package dvdrent;
/* http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/#getting-started-with-java-driver */

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import models.ClientModel;
import models.DvdModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class DBHelperImpl implements DBHelperInterface{
	private static MongoClient mongoClient;
	private static DB db;
	private static final String CLIENT = "clientCollection";
	private static final String DVD = "dvdCollection";
	private static final String DB_NAME = "mydb";
	
	DBHelperImpl(){
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		db = mongoClient.getDB(DB_NAME);
		dropCollections();
		db.createCollection(CLIENT, new BasicDBObject("capped", true).append("size", 1048576));
		db.createCollection(DVD, new BasicDBObject("capped", true).append("size", 1048576));
		getListOfCollections();
	}
	
	private static void getListOfCollections(){
		for (String s : db.getCollectionNames()) {
			   System.out.println(s);
			}
	}
	
	private static void dropCollections(){
		DBCollection testCollection = db.getCollection(CLIENT);
		testCollection.drop();
		testCollection = db.getCollection(DVD);
		testCollection.drop();
	}
	
	@Override
	public Integer addClient(ClientModel client) {
		DBCollection coll = db.getCollection(CLIENT);
		BasicDBObject doc = new BasicDBObject("name", client.getName())
        .append("surname",client.getSurname())
        .append("phone", client.getPhone());
		WriteResult wr = coll.insert(doc); 
		return (Integer) wr.getField("id");	//TODO trzeba jakoś zwrócić id nowo dodanego klienta
	}

	@Override
	public boolean removeClient(int clientId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer addDVD(DvdModel dvd) {
		DBCollection coll = db.getCollection(DVD);
		BasicDBObject doc = new BasicDBObject("title", dvd.getTitle())
        .append("genre",dvd.getGenre())
        .append("year", dvd.getYear())
        .append("lenght", dvd.getLenght());
		WriteResult wr = coll.insert(doc); 
		return (Integer) wr.getField("id");	//TODO trzeba jakoś zwrócić id nowo dodanej plyty
	}

	@Override
	public boolean removeDVD(int dvdId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ClientModel> listClients() {
		LinkedList<ClientModel> clients = new LinkedList<ClientModel>();
		DBCollection coll = db.getCollection(CLIENT);
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next();
				ClientModel cm = new ClientModel((int) doc.get("id"),(String) doc.get("name"), (String)doc.get("surname"),(String) doc.get("phone"));
				clients.add(cm);
			}
		}finally{
			cursor.close();
		}
		return clients;
	}

	@Override
	public List<DvdModel> listDvds() {
		LinkedList<DvdModel> dvds = new LinkedList<DvdModel>();
		DBCollection coll = db.getCollection(DVD);
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next(); // int id, String title, String genre, int year, int lenght, ClientModel rentedBy
				DvdModel dm = new DvdModel((int) doc.get("id"),(String) doc.get("title"), (String)doc.get("genre"),(int) doc.get("year"), (int) doc.get("lenght"));
				Integer rentedById = (Integer) doc.get("rentedBy"); 
				
				if(rentedById!= null){
					dm.setRentedBy(rentedById);
				}
				dvds.add(dm);
			}
		}finally{
			cursor.close();
		}
		return dvds;
	}

	@Override
	public List<DvdModel> listRentedDvds() {
		LinkedList<DvdModel> rentedDvds = new LinkedList<DvdModel>();
		DBCollection coll = db.getCollection(DVD);
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next(); // int id, String title, String genre, int year, int lenght, ClientModel rentedBy
				DvdModel dm = new DvdModel((int) doc.get("id"),(String) doc.get("title"), (String)doc.get("genre"),(int) doc.get("year"), (int) doc.get("lenght"));
				Integer rentedById = (Integer) doc.get("rentedBy"); 
				
				if(rentedById!= null){
					dm.setRentedBy(rentedById);
					rentedDvds.add(dm);
				}
			}
		}finally{
			cursor.close();
		}
		return rentedDvds;
	}

	@Override
	public List<ClientModel> listClientWhoRentSomething() {
		LinkedList<ClientModel> clientsWhoRent = new LinkedList<ClientModel>();
		DBCollection coll = db.getCollection(DVD);
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next(); // int id, String title, String genre, int year, int lenght, ClientModel rentedBy
				Integer rentedById = (Integer) doc.get("rentedBy"); 
				
				if(rentedById!= null){
					ClientModel cm = findClient(rentedById);
					if(!clientsWhoRent.contains(cm)){
						clientsWhoRent.add(cm);
					}
				}
			}
		}finally{
			cursor.close();
		}
		return clientsWhoRent;
	}

	@Override
	public ClientModel findClient(int clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DvdModel findDvd(int dvdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rentDvd(int clientId, int dvdId) {
		ClientModel cm = findClient(clientId);
		DvdModel dm = findDvd(dvdId);
		if(cm!= null && dm!=null){
			dm.setRentedBy(cm.getId());
			if(updateDVD(dm) == dvdId)
				return true;
			else 
				return false;
		}
		return false;
	}

	@Override
	public boolean returnDvd(int dvdId) {
		DvdModel dm = findDvd(dvdId);
		if(dm!=null){
			dm.setRentedBy(null);
			if(updateDVD(dm) == dvdId)
				return true;
			else 
				return false;
		}
		return false;
	}

	@Override
	public Integer updateDVD(DvdModel dvd) {
		// TODO Auto-generated method stub
		return null;
	}

}
