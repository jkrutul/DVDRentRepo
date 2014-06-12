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
public class DvdModel {
    private Integer id;
    private String title, genre;
    private int year, lenght;
    private Integer rentedById; 

    public DvdModel(String title, String genre, int year, int lenght) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.lenght = lenght;
    }

    public DvdModel(int id, String title, String genre, int year, int lenght) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.lenght = lenght;
    }
    
    public DvdModel(int id, String title, String genre, int year, int lenght, Integer rentedById) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.lenght = lenght;
        this.rentedById = rentedById;
    }

    public Integer getRentedBy() {
        return rentedById;
    }

    public void setRentedBy(Integer rentedById) {
        this.rentedById = rentedById;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    @Override
    public String toString() {
    	if(rentedById != null)
    		return "DvdModel{" + "id=" + id + ", title=" + title + ", genre=" + genre + ", year=" + year + ", lenght=" + lenght + ", rentedById=" + rentedById + '}';
    	else{
    		return "DvdModel{" + "id=" + id + ", title=" + title + ", genre=" + genre + ", year=" + year + ", lenght=" + lenght + " - is avaliable}";
    	}
    }
    
    
   
    
}
