/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author krutulj
 */
public class ClientModel {
    private int id;
    private String name, surname;
    
    public ClientModel(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public ClientModel(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    

}
