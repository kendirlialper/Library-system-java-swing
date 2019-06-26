package Database;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Date;


public class Transaction {

    private Date d_of_desposit;
    private Date delivery_date;
    private String userID;
    private int bookID;

    public Transaction() {
    }

    public Date getD_of_desposit() {
        return d_of_desposit;
    }

    public void setD_of_desposit(Date d_of_desposit) {
        this.d_of_desposit = d_of_desposit;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
}
