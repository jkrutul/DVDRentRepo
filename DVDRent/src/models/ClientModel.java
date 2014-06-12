/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import org.bson.types.ObjectId;

/**
 *
 * @author krutulj
 */
public class ClientModel extends BaseModel {
    
    private String name, surname, phone;
    
    public ClientModel(ObjectId id, String name, String surname, String phone) {
    	super(id);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public ClientModel(String name, String surname, String phone) {
        super(null);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "ClientModel{" + "id=" + id + ", name=" + name + ", surname=" + surname + '}';
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    

}
