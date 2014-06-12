package models;

import org.bson.types.ObjectId;

public class BaseModel {
	
	protected ObjectId generator=new ObjectId();
	
	protected ObjectId id=null;
	
	public BaseModel(ObjectId id) {
		this.id=id;
	}

	public void generateId()
	{
		this.id=ObjectId.get();
	}
	
	boolean isNew()
	{
		return id != null ? true : false;
	}
	
    public ObjectId getId() {
        return id;
    }

}
