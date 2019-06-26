package Database;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.SQLException;


public class User {

    private  String TC;
    private  String name ;
    private  String surname;
    private  float phone;
    private  String mail ;
    private  int authorityID;
    private  String password ;

    public String getTC() {
        return TC;
    }

    public void setTC(String TC) {
        this.TC = TC;
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

    public float getPhone() {
        return phone;
    }

    public void setPhone(float phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAuthorityID() {
        return authorityID;
    }

    public void setAuthorityID(int authorityID) {
        this.authorityID = authorityID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        System.out.println("kisi olusturuldu");
    }

    public Boolean passControl() throws SQLException, InstantiationException{
        LSDB lsdb = new LSDB();
       // User user1 = new User();
      //  user1 = lsdb.userControl(mail, password);
        if ( lsdb.userControl(mail , password) != null){
            System.out.print("giris basarılı");
            return true;
        }
        else{
            System.out.print("giris basarısız");
            return false;
        }
    }


    public static void main(String []agrs) throws SQLException, InstantiationException{
        User u = new User();
        u.setMail("aaa.aaa");
        u.setPassword("123456");
        System.out.println(u.passControl());
    }

}
