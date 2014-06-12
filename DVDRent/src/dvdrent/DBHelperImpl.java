package dvdrent;
/* http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/#getting-started-with-java-driver */

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;

import models.ClientModel;
import models.DvdModel;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
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
		db.createCollection(CLIENT, new BasicDBObject("capped", false).append("size", 1048576));
		db.createCollection(DVD, new BasicDBObject("capped", false).append("size", 1048576));
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
	public ObjectId addClient(ClientModel client) {
		client.generateId();
		
		DBCollection coll = db.getCollection(CLIENT);
		BasicDBObject doc = new BasicDBObject("name", client.getName())
        .append("surname",client.getSurname())
        .append("phone", client.getPhone()).append("id", client.getId());
		
		WriteResult wr = coll.insert(doc); 
		return client.getId();
	}

	@Override
	public boolean removeClient(ObjectId clientId) {
		DBCollection coll = db.getCollection(CLIENT);
		BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
		BasicDBObject query = new BasicDBObject("id", clientId);
		builder.find(query).removeOne();
		BulkWriteResult result = builder.execute();
		List<DvdModel> rented=listRentedDvds();
		for(DvdModel processed : rented)
		{
			if(processed.getRentedBy().equals(clientId))
			{
				processed.setRentedBy(null);
				updateDVD(processed);
			}
		}
		
		return result.getRemovedCount() > 0 ? true : false;
	}

	@Override
	public ObjectId addDVD(DvdModel dvd) {
		dvd.generateId();
		
		DBCollection coll = db.getCollection(DVD);
		BasicDBObject doc = new BasicDBObject("title", dvd.getTitle())
        .append("genre",dvd.getGenre())
        .append("year", dvd.getYear())
        .append("lenght", dvd.getLenght()).append("id", dvd.getId());
		WriteResult wr = coll.insert(doc); 
		return dvd.getId();
	}

	@Override
	public boolean removeDVD(ObjectId dvdId) {
		DBCollection coll = db.getCollection(CLIENT);
		BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
		BasicDBObject query = new BasicDBObject("id", dvdId);
		builder.find(query).removeOne();
		BulkWriteResult result = builder.execute();
		return result.getRemovedCount() > 0 ? true : false;
	}

	@Override
	public List<ClientModel> listClients() {
		LinkedList<ClientModel> clients = new LinkedList<ClientModel>();
		DBCollection coll = db.getCollection(CLIENT);
		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){
				DBObject doc = cursor.next();
				ClientModel cm = new ClientModel((ObjectId) doc.get("id"),(String) doc.get("name"), (String)doc.get("surname"),(String) doc.get("phone"));
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
				DvdModel dm = new DvdModel((ObjectId) doc.get("id"),(String) doc.get("title"), (String)doc.get("genre"),(int) doc.get("year"), (int) doc.get("lenght"));
				ObjectId rentedById = (ObjectId) doc.get("rentedBy"); 
				
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
				DvdModel dm = new DvdModel((ObjectId) doc.get("id"),(String) doc.get("title"), (String)doc.get("genre"),(int) doc.get("year"), (int) doc.get("lenght"));
				ObjectId rentedById = (ObjectId) doc.get("rentedBy"); 
				
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
				ObjectId rentedById = (ObjectId) doc.get("rentedBy"); 
				
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

	//FIXME
	@Override
	public ClientModel findClient(ObjectId clientId) {
		DBCollection coll = db.getCollection(CLIENT);
		BasicDBObject query = new BasicDBObject("id", clientId);
		DBObject doc = coll.findOne(query);
		ClientModel cm = new ClientModel((ObjectId) doc.get("id"),(String) doc.get("name"), (String)doc.get("surname"),(String) doc.get("phone"));
		return cm;
	}

	@Override
	public DvdModel findDvd(ObjectId dvdId) {
 		DBCollection coll = db.getCollection(DVD);
		BasicDBObject query = new BasicDBObject("id", dvdId);
		DBObject doc = coll.findOne(query);
		DvdModel dm = new DvdModel((ObjectId) doc.get("id"),(String) doc.get("title"), (String)doc.get("genre"),(int) doc.get("year"), (int) doc.get("lenght"));
		return dm;
	}

	@Override
	public boolean rentDvd(ObjectId clientId, ObjectId dvdId) {
		ClientModel cm = findClient(clientId);
		DvdModel dm = findDvd(dvdId);
		if(cm!= null && dm!=null){
			
			dm.setRentedBy(cm.getId());
			if(updateDVD(dm).equals(dvdId))
				return true;
			else 
				return false;
			
		}
		
		return false;
	}

	@Override
	public boolean returnDvd(ObjectId dvdId) {
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
	public ObjectId updateDVD(DvdModel dvd) {
		DBCollection coll = db.getCollection(DVD);
		BasicDBObject query = new BasicDBObject("id", dvd.getId());
		
		BasicDBObject doc = new BasicDBObject("title", dvd.getTitle())
        .append("genre",dvd.getGenre())
        .append("year", dvd.getYear())
        .append("lenght", dvd.getLenght()).append("id", dvd.getId()).append("rentedBy", dvd.getRentedBy());
		
		BasicDBObject update=new BasicDBObject();
		update.append("$set", doc);
		
		WriteResult result=coll.update(query, update);
		
		
		System.out.println("Updated records: "+result.toString());
		
		return dvd.getId();
	}

}
