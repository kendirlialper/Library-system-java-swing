package DB;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Date;


public class Books {
    private int idBook;
    private String name ;
    private int year_of_printig;
    private int publisherID;
    private int categoriesID;
    private boolean is_there;
    private Date insertDate;

    public Books() {

    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear_of_printig() {
        return year_of_printig;
    }

    public void setYear_of_printig(int year_of_printig) {
        this.year_of_printig = year_of_printig;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public int getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(int categoriesID) {
        this.categoriesID = categoriesID;
    }

    public boolean isIs_there() {
        return is_there;
    }

    public void setIs_there(boolean is_there) {
        this.is_there = is_there;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}
