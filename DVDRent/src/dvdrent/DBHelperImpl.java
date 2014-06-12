package dvdrent;
/* http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/#getting-started-with-java-driver */
import java.awt.List;
import java.net.UnknownHostException;

import models.ClientModel;
import models.DvdModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
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
	public boolean removeClient(Integer clientId) {
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
	public boolean removeDVD() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List listClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listDvds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listRentedDvds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listClientWhoRentSomething() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel findClient(Integer clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DvdModel findDvd(Integer dvdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rentDvd(Integer clientId, Integer dvdId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean returnDvd(Integer dvdId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer updateDVD(DvdModel dvd) {
		// TODO Auto-generated method stub
		return null;
	}

}
