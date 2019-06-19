package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KayademirS
 */
public class LSDB {

    //veritabanına baglantı sağlıyor
    private Connection connection ;
    String url = "jdbc:mysql://localhost:3306/librarysystem";
    String user = "root";
    String password = "sedaseda";

    //Veritabanına baglanıyor
    public Connection Open() throws InstantiationException, SQLException{
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Open");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LSDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LSDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    //Admin girişi için kontrol yapıyor
    public User adminControl(String mail , String password) throws InstantiationException, SQLException{
        String sql = "select * from user where authorityID='1' and mail = ?  and password = ? ";
        if (connection == null){
            System.out.println("Baglantı kurulmaya çalışılıyor");
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, mail);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        User u = null;
        if(rs.next()){
            u = new User();
            u.setMail(rs.getString("mail"));
            u.setPassword(rs.getString("password"));
        }
        return u;
    }

    //Librarian giriş için kontrol yapıyor
    public User librarianControl(String mail , String password) throws InstantiationException, SQLException{
        String sql = "select * from user where authorityID='2' and mail = ?  and password = ? ";
        if (connection == null){
            System.out.println("Baglantı kurulmaya çalışılıyor");
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, mail);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        User u = null;
        if(rs.next()){
            u = new User();
            u.setMail(rs.getString("mail"));
            u.setPassword(rs.getString("password"));
        }
        return u;
    }

    // librarian member için kontrol yapıyor
    public User userControl(String mail , String password) throws InstantiationException, SQLException{
        String sql = "select * from user where authorityID='3' and mail = ?  and password = ? ";
        if (connection == null){
            System.out.println("Baglantı kurulmaya çalışılıyor");
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, mail);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        User u = null;
        if(rs.next()){
            u = new User();
            u.setMail(rs.getString("mail"));
            u.setPassword(rs.getString("password"));
        }
        return u;
    }

    public List<Date> dateStatistic() throws SQLException, InstantiationException {
        String sql="select dateofdesposit from librarysystem.transaction group by dateofdesposit;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Date> list=null;
        if(rs.next()){
            list = new ArrayList<Date>();

        }
        rs.beforeFirst();
        while ( rs.next()) {
            Date date=new Date();
            list.add(date);

            if (list.isEmpty() == true) {
                System.out.print("liste bos");
            } else {
                System.out.print(rs.getString(1) + "\t");

            }
        }

        pstmt.close();
        rs.close();
        return list;
    }
    public Double countW=1.0,countE=1.0,countN=1.0,countT=1.0;
    public void pieStatistic(Books books) throws SQLException, InstantiationException {
        String sql="select categories_ID from librarysystem.books where idBooks=?;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1 ,books.getIdBook() );
        int catID=pstmt.executeUpdate();
        pstmt.close();
        switch (catID){
            case 1: countW++;

            case 2:countE++;

            case 3:countN++;

            case 4:countT++;

        }
    }

    //kitapların listesini cekiyor //
    // true parametre verilirse suanda kütüphanede olan kitapları
    // false parametre verilirse ödünç verilenleri getiriyor
    public List<Books> booksList(boolean is_there ) throws InstantiationException, SQLException{
        String sql = " Select * From books where is_there = ? ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setBoolean(1, is_there);
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            b.setIdBook(rs.getInt("idBooks"));
            b.setName(rs.getString("name"));
            b.setPublisherID(rs.getInt("publisher_ID"));
            b.setCategoriesID(rs.getInt("categories_ID"));
            b.setYear_of_printig(rs.getInt("year_of_printing"));
            list.add(b);
            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(2) +"\t");
                System.out.print(rs.getString(4)  +"\t");
                System.out.println(rs.getString(5));

            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // İade tarih, gecmiş kitapları getiriyor
    public List<Books> booksList( ) throws InstantiationException, SQLException{
        String sql = " select dateofdesposit , deliverydate , concat(user.name ,'  ', user.surname) as 'Name Surname' , books.name as 'Book Name'\n" +
                "from librarysystem.transaction , librarysystem.books , librarysystem.user\n" +
                " where deliverydate <= CurDate() and user.TC = transaction.userID  and books.idBooks = transaction.bookID;  ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Transaction t = new Transaction();
            User u = new User();
            t.setD_of_desposit(rs.getDate("dateofdesposit"));
            t.setDelivery_date(rs.getDate("deliverydate"));
            b.setName(rs.getString("Book Name"));
            u.setName(rs.getString("Name Surname"));



            list.add(b);
            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(1) +"\t");
                System.out.print(rs.getString(2)  +"\t");
                System.out.print(rs.getString(3)  +"\t");
                System.out.println(rs.getString(4));

            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // librarian member (kütüphane üyelerini) getiriyor
    public List<User> listUser() throws InstantiationException, SQLException{
        String sql = " select * from user where authorityID = '3' ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        List <User> list = null;
        if(rs.next()){
            list = new ArrayList<User>();
        }
        rs.beforeFirst();
        while ( rs.next()){
            User u = new User();
            u.setTC(rs.getString("TC"));
            u.setName(rs.getString("name"));
            u.setSurname(rs.getString("surname"));
            u.setPhone((rs.getFloat("Phone")));
            u.setMail((rs.getString("mail")));
            u.setPassword(rs.getString("password"));
            u.setAuthorityID(rs.getInt("authorityID"));
            list.add(u);

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(2) +"\t");
                System.out.println(rs.getString(3)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // Adminlerin listesini getiriyor
    public List<User> listAdmin() throws InstantiationException, SQLException{
        String sql = " select * from user where authorityID = '1' ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        List <User> list = null;
        if(rs.next()){
            list = new ArrayList<User>();

        }
        rs.beforeFirst();
        while ( rs.next()){

            User u = new User();
            u.setTC(rs.getString("TC"));
            u.setName(rs.getString("name"));
            u.setSurname(rs.getString("surname"));
            u.setPhone((rs.getFloat("Phone")));
            u.setMail((rs.getString("mail")));
            u.setPassword(rs.getString("password"));
            u.setAuthorityID(rs.getInt("authorityID"));
            list.add(u);


            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(2) +"\t");
                System.out.println(rs.getString(3)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // kütüphane calısanlarının listesini getiriyor
    public List<User> listLibrarian() throws InstantiationException, SQLException{
        String sql = " select * from user where authorityID = '2' ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        List <User> list = null;
        if(rs.next()){
            list = new ArrayList<User>();


        }


        rs.beforeFirst();
        while ( rs.next()){
            User u = new User();
            u.setTC(rs.getString("TC"));
            u.setName(rs.getString("name"));
            u.setSurname(rs.getString("surname"));
            u.setPhone((rs.getFloat("Phone")));
            u.setMail((rs.getString("mail")));
            u.setPassword(rs.getString("password"));
            u.setAuthorityID(rs.getInt("authorityID"));
            list.add(u);

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(2) +"\t");
                System.out.println(rs.getString(3)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // üyenin aldığı kitapları listeliyor
    public List<Books> booksListUser(int userID) throws InstantiationException, SQLException{
        String sql = "select  books.name , user.name  from librarysystem.books , librarysystem.transaction , librarysystem.user\n" +
                "where transaction.bookID = books.idBooks and transaction.userID =? \n" +
                "and transaction.userID = user.TC and transaction.dateofdesposit <= curdate();  ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, userID);
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Transaction t = new Transaction();
            list.add(b);

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(1) +"\t");
                System.out.println(rs.getString(2)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    //kitapları kategorisine göre sıralıyor
    public List<Books> booksListCategories() throws InstantiationException, SQLException{
        String sql = " Select books.name ,  publisher.name ,  categories.name from librarysystem.books , librarysystem.publisher , librarysystem.categories\n" +
                " where books.categories_ID = categories.idcategories and books.publisher_ID = publisher.idpublisher  Order By  categories.name ; ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Transaction t = new Transaction();
            list.add(b);

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(3) +"\t");
                System.out.print(rs.getString(2)  +"\t");
                System.out.println(rs.getString(1)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    //kitapları yayınevine göre sıralıyor
    public List<Books> booksListPublisher() throws InstantiationException, SQLException{
        String sql = "  Select books.name ,  publisher.name ,  categories.name from librarysystem.books , librarysystem.publisher , librarysystem.categories\n" +
                " where books.categories_ID = categories.idcategories and books.publisher_ID = publisher.idpublisher  Order By  publisher.name;  ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()) {
            Books b = new Books();
            Transaction t = new Transaction();
            list.add(b);

            if (list.isEmpty() == true) {
                System.out.print("liste bos");
            } else {
                System.out.print(rs.getString(2) + "\t");
                System.out.print(rs.getString(1) + "\t");
                System.out.println(rs.getString(3) + "\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }


    // kitapların içinde verilen parametreye göer arama yapıyor
    public List<Books> booksListSearch(String index) throws InstantiationException, SQLException{
        String sql = "  Select books.name as book ,  publisher.name as publisher ,  categories.name as categories from librarysystem.books , librarysystem.publisher , librarysystem.categories\n" +
                " where books.categories_ID = categories.idcategories and books.publisher_ID = publisher.idpublisher and books.name like ?  ;  ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 , "%"+index+"%" );
        ResultSet rs = pstmt.executeQuery();
        List <Books> list = null;
        if(rs.next()){
            list = new ArrayList<Books>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Categories c = new Categories();
            Publisher p = new Publisher();
            b.setName(rs.getString("book"));
            c.setName(rs.getString("categories"));
            p.setName(rs.getString("publisher"));
            list.add(b);

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString(2)  +"\t");
                System.out.print(rs.getString(1) +"\t");
                System.out.println(rs.getString(3)  +"\t");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // kitabını ödünç alıp verme işlemini yapıyor
    public boolean books_thereis(Books b) throws InstantiationException, SQLException{
        String sql = "update librarysystem.books set is_there = ? where  idBooks = ? ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setBoolean(1 , b.isIs_there());
        pstmt.setInt(2 ,  b.getIdBook());
        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    // Kitap ekleme
    // NOT : BU TAMAMLANMADI AYNI ANDA YAZARA DA EKLEME YAPILMASI GEREK
    public boolean booksInsert(Books b) throws SQLException, InstantiationException{
        String sql = "Insert Into librarysystem.books ( insertdate ,name , year_of_printing , publisher_ID , categories_ID , is_there )\n" +
                "value (current_date ,? , ? , ? , ? , ? ) ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 , b.getName());
        pstmt.setInt(2 ,  b.getYear_of_printig());
        pstmt.setInt(3 ,  b.getPublisherID());
        pstmt.setInt(4 ,  b.getCategoriesID());
        pstmt.setBoolean(5 , true);

        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    //girilen isimde yazar var ise onun idsini cekiyor
    public int authorControl(Author a) throws InstantiationException, SQLException{
        String sql = "Select idAuthor from author where author.name = ? and author.surname = ? ;";
        int id = -1;
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, a.getName());
        pstmt.setString(2, a.getSurname());
        ResultSet rs = pstmt.executeQuery();
        List <Author> list = null;
        if(rs.next()){
             a.setIdAuthor(rs.getInt("idAuthor"));
              id = a.getIdAuthor();
        }
        return id;

    }
    public int booksControl(Books b) throws SQLException, InstantiationException {
        String sql = "SELECT idBooks from books where  insertdate = current_date and name = ?";
        int id = -1;
        if(connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 , b.getName());
        ResultSet rs = pstmt.executeQuery();
        if (rs.last()){
            b.setIdBook(rs.getInt("idBooks"));
            id = b.getIdBook();
        }
        return id;
    }

    public  boolean BooksByAuothorInsert(BooksByAuthor booksByAuthor) throws SQLException, InstantiationException {
        String sql = "INSERT INTO booksbyauthor (bookID, authorID)  VALUES (? , ? )";
        if(connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, booksByAuthor.getBookID());
        pstmt.setInt(2, booksByAuthor.getAuthorID());

        int result = pstmt.executeUpdate();
        pstmt.close();
        return (result > 0);
    }

    public boolean AuthorInsert(Author a) throws SQLException, InstantiationException {
        String sql = "INSERT INTO `librarysystem`.`author` ( `name`, `surname`) VALUES (? , ?);";
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 ,a.getName());
        pstmt.setString(2 ,a.getSurname());

        int result =pstmt.executeUpdate();
        pstmt.close();
        return (result > 0);
    }

    // Kütüphane üyesi ekliyor
    public boolean UserInsert(User u) throws SQLException, InstantiationException{
        String sql = "Insert Into librarysystem.user (TC , name ,surname , phone , mail , authorityID , password)" +
                "value (? ,? , ? , ? , ? , '3' , ? ) ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, u.getTC());
        pstmt.setString(2 , u.getName());
        pstmt.setString(3 , u.getSurname());
        pstmt.setFloat(4 , u.getPhone() );
        pstmt.setString(5 , u.getMail());
        pstmt.setString(6 , u.getPassword() );

        int result = pstmt.executeUpdate();
        pstmt.close();
        return (result >0);

    }

    // kütüphane calısanı ekliyor
    public boolean LibrarianInsert(User u) throws SQLException, InstantiationException{
        String sql = "Insert Into librarysystem.user (TC ,name ,surname , phone , mail , authorityID , password)" +
                "value (? , ? , ? , ? , ? , '2' , ? ) ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, u.getTC());
        pstmt.setString(2 , u.getName());
        pstmt.setString(3 , u.getSurname());
        pstmt.setFloat(4 , u.getPhone() );
        pstmt.setString(5 , u.getMail());
        pstmt.setString(6 , u.getPassword() );

        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    //Transaction ekleme
    public boolean TransactionInsert(Transaction t) throws SQLException, InstantiationException {
        String sql = "Insert Into librarysystem.transaction (dateofdesposit , deliverydate , userID , bookID )\n" +
                "values ( CurDate() , dateofdesposit + interval 15 day , ? , ? ) ;";

        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, t.getUserID());
        pstmt.setInt(2,t.getBookID());

        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);

    }

    public Transaction TrancationInfo(int bookID) throws SQLException, InstantiationException {
        String sql = " select dateofdesposit , deliverydate , userID , books.name as 'Book Name' " +
                "from librarysystem.transaction , librarysystem.books , librarysystem.user " +
                " where books.idBooks = transaction.bookID and transaction.bookID = ? ";

        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1 , bookID);
        ResultSet rs = pstmt.executeQuery();
        Transaction t  = null;
        if(rs.next()){
            t = new Transaction();
            Books b = new Books();
            t.setD_of_desposit(rs.getDate("dateofdesposit"));
            t.setDelivery_date(rs.getDate("deliverydate"));
            t.setUserID(rs.getString("userID"));
            b.setName(rs.getString("Book Name"));
        }
        pstmt.close();
        rs.close();
        return t ;
    }

    public List<String> TrancationList() throws SQLException, InstantiationException {
        String sql = "select transaction.bookID , transaction.dateofdesposit , transaction.userID ," +
                " transaction.deliverydate , librarysystem.books.idBooks , librarysystem.books.name " +
                ", user.TC , librarysystem.user.name ,librarysystem.user.surname " +
                "from librarysystem.transaction  " +
                "JOIN librarysystem.books ON transaction.bookID = books.idBooks " +
                "JOIN librarysystem.user ON transaction.userID = user.TC;";

        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<String> list = null;
        if(rs.next()){
            list = new ArrayList<>();
        }
        rs.beforeFirst();
        while ( rs.next()){
            Transaction t = new Transaction();
            Books b = new Books();
            User u = new User();
            u.setName(rs.getString("user.name"));
            u.setTC(rs.getString("user.TC"));
            u.setSurname(rs.getString("user.surname"));
            t.setUserID(rs.getString("transaction.userID"));
            t.setD_of_desposit(rs.getDate("transaction.dateofdesposit"));
            t.setDelivery_date(rs.getDate("transaction.deliverydate"));
            t.setBookID(rs.getInt("transaction.bookID"));
            b.setName(rs.getString("books.name"));
            b.setIdBook(rs.getInt("books.idBooks"));
            list.add(String.valueOf(b.getIdBook()));
            list.add( u.getTC());
            list.add( u.getName());
            list.add( u.getSurname());
            list.add( t.getD_of_desposit().toString());
            list.add( t.getDelivery_date().toString());
            list.add( b.getName());
        }
        if ( list != null){
            System.out.print(list.get(0) +"\t" );
            System.out.print(list.get(1) +"\t" );
            System.out.print(list.get(2) +"\t" );
            System.out.print(list.get(3) +"\t" );
            System.out.print(list.get(4) +"\t" );
            System.out.print(list.get(5) +"\t" );

        }
        pstmt.close();
        rs.close();
        return list;
    }

    public boolean  TransactionDelete(Transaction t) throws SQLException{
        String sql = "delete from librarysystem.transaction where userID =? and bookID=? and dateofdesposit = ? ; " ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, t.getUserID());
        pstmt.setInt(2 , t.getBookID());
        pstmt.setDate(3,t.getD_of_desposit());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;

    }

    public boolean DebtInsert (Debts d) throws SQLException, InstantiationException {
        String sql = "Insert into librarysystem.debts ( debts.debt , debts.TC , debts.bookID ) " +
                "values (?, ? ,?) ;";

        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setFloat(1 , d.getDebts() );
        pstmt.setString(2, d.getTC() );
        pstmt.setInt(3 ,d.getBookID());
        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    public boolean DebtUpdate (Debts d) throws SQLException, InstantiationException {
        String sql = " update librarysystem.debts set debt = ? where TC = ? and bookID = ?;";

        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setFloat(1 , d.getDebts() );
        pstmt.setString(2, d.getTC() );
        pstmt.setInt(3 ,d.getBookID());
        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    public List<?> DebtList () throws SQLException, InstantiationException {
        String sql = "SELECT debts.TC , user.name , user.surname , " +
                "books.name, debts.bookID , debt " +
                "FROM librarysystem.debts " +
                "Inner Join librarysystem.user " +
                "ON debts.TC = user.TC " +
                "Inner Join librarysystem.books " +
                "ON debts.bookID = books.idBooks;";
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List list = null;
        if(rs.next()){
            list = new ArrayList<>();
        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Debts d = new Debts();
            User u = new User();
            b.setIdBook(rs.getInt("debts.bookID"));
            b.setName(rs.getString("books.name"));
            d.setBookID(rs.getInt("debts.bookID"));
            d.setTC(rs.getString("debts.TC"));
            d.setDebts(rs.getFloat("debt"));
            u.setTC(rs.getString("debts.TC"));
            u.setName(rs.getString("user.name"));
            u.setSurname(rs.getString("user.surname"));
            list.add(b.getName());
            list.add(d.getBookID());
            list.add(d.getTC());
            list.add(d.getDebts());
            list.add(u.getName());
            list.add(u.getSurname());

        }
        return list;
    }

    public boolean DebtsDelete(Debts d) throws SQLException, InstantiationException {
        String sql = "delete from librarysystem.debts where TC =? and bookID = ?; " ;
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, d.getTC());
        pstmt.setInt(2, d.getBookID());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;

    }
    //verien tc deki kişiyi siliyor
    public boolean UserDelete(User u) throws SQLException, InstantiationException {
        String sql = "delete from librarysystem.user where TC =? and authorityID='3' " ;
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, u.getTC());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;

    }

    public boolean LibrarianDelete(User u) throws SQLException, InstantiationException {
        String sql="delete from librarysystem.user where TC =? and authorityID='2'";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, u.getTC());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;
    }
   public ArrayList<Double> CategoriesCount() throws SQLException, InstantiationException {
        String sql = "select categories_ID , dateofdesposit from librarysystem.transaction INNER JOIN librarysystem.books ON transaction.bookID = books.idBooks  where categories_ID  = ? ";
        List <Categories> categories = categoriesList();
        ArrayList <Double> countlist = new ArrayList<Double>();
       double count = 0.0;
        if(connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = null;
        for (int i = 1 ; i <= categories.size() ; i++){

        pstmt.setInt(1 , i);
         rs = pstmt.executeQuery();
            if (rs.last()) {
                rs.getRow();
            }
            count  = rs.getRow();
            countlist.add(count);
        }
        return countlist;
   }
    public ArrayList<Double> CategoriesCountDate(java.sql.Date date) throws SQLException, InstantiationException {
        String sql = "SELECT  categories_ID from transaction INNER JOIN books ON transaction.bookID = books.idBooks where dateofdesposit = ? and categories_ID =?";
        List <Categories> categories = categoriesList();
        ArrayList <Double> countlist = new ArrayList<Double>();
        double count = 0.0;
        if(connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = null;
        pstmt.setDate(1 ,  date);
        for (int i = 1 ; i <= categories.size() ; i++){

            pstmt.setInt(2, i);
            rs = pstmt.executeQuery();
            if (rs.last()) {
                rs.getRow();
            }
            count  = rs.getRow();
            countlist.add(count);
        }

        return countlist;
    }

    public List<Categories> categoriesList() throws SQLException, InstantiationException {
        String sql = "Select * from categories";
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Categories> list = null;
        if(rs.next()){
            list = new ArrayList<Categories>();
        }
        rs.beforeFirst();
        while ( rs.next()){
           Categories c = new Categories();
           c.setIdCategories(rs.getInt("idcategories"));
           c.setName(rs.getString("name"));
           list.add(c);

        }
        return list;
    }

    public List<Publisher> publisherList() throws SQLException, InstantiationException {
        String sql = "Select * from publisher";
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Publisher> list = null;
        if(rs.next()){
            list = new ArrayList<Publisher>();
        }
        rs.beforeFirst();
        while ( rs.next()){
            Publisher p = new Publisher();
            p.setIdPublisher(rs.getInt("idpublisher"));
            p.setName(rs.getString("name"));
            list.add(p);

        }
        return list;
    }

    public List<Author> authorList() throws SQLException, InstantiationException {
        String sql = "Select * from author";
        if ( connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Author> list = null;
        if(rs.next()){
            list = new ArrayList<Author>();
        }
        rs.beforeFirst();
        while ( rs.next()){
        Author a = new Author();
        a.setIdAuthor(rs.getInt("idAuthor"));
        a.setName(rs.getString("name"));
        a.setSurname(rs.getString("surname"));
        a.setPhone(rs.getFloat("phone"));
        a.setMail(rs.getString("mail"));
        list.add(a);

        }
        return list;
    }

    //tüm kitapları ceker
    public List<String> booksListAll( ) throws InstantiationException, SQLException{
        String sql = " select books.name as Name , books.year_of_printing as Year ,categories.name as Categories, " +
                "publisher.name as Publisher from librarysystem.books  " +
                "JOIN librarysystem.categories " +
                "ON books.categories_ID = categories.idcategories " +
                "JOIN librarysystem.publisher " +
                "ON books.publisher_ID = publisher.idpublisher " +
                "Order By  books.name ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List <String> list = null;
        if(rs.next()){
            list = new ArrayList<String>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Categories c = new Categories();
            Publisher p = new Publisher();
            b.setName(rs.getString("Name"));
            b.setYear_of_printig(rs.getInt("Year"));
            c.setName(rs.getString("Categories"));
            p.setName(rs.getString("Publisher"));
            list.add(b.getName());
            list.add(String.valueOf(b.getYear_of_printig()));
            list.add(c.getName());
            list.add(p.getName());


            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
            else{
                System.out.print(rs.getString("Name") +"\t");
                //  System.out.print(rs.getString(1)  +"\t");
                //   System.out.println(rs.getString(2));

            }
        }

        pstmt.close();
        rs.close();
        return list;
    }
////////////////////  TEST METODU //////////////////////////////
    public static void main(String args[]) throws InstantiationException, SQLException{
        LSDB lsdb = new LSDB();
    //    lsdb.booksListAll();
      //  lsdb.userControl( "aaa.aaa" , "123456");
      //  lsdb.booksList(true);
      //  System.out.println("------------------");
       // lsdb.booksListUser(9);
      //  System.out.println("------------------");
      //  lsdb.booksListCategories();
     //   System.out.println("------------------");
      //  lsdb.booksListPublisher();
      //  System.out.println("------------------");
     //   lsdb.booksListSearch("t");
      //  System.out.println("------------------");
      //  lsdb.listUser();
      //  System.out.println("------------------");
      //  lsdb.listAdmin();
      //  System.out.println("------------------");
      //  lsdb.listLibrarian();
      //  System.out.println("------------------");
       // lsdb.booksList();
      //  System.out.println("------------------");
     //   Author a = new Author();
      //  a.setName("sabahattin");
      //  a.setSurname("ali");
      //  lsdb.authorControl(a);
      //  System.out.println("------------------");
      //        lsdb.listLibrarian();
        // if (lsdb.authorControl(a) != null){
        // Books b = new Books();
        // b.setName("ucurtma avcısı");
        // b.setYear_of_printig(1999);
        // b.setPublisherID(1);
        // b.setCategoriesID(2);
        //  b.setIs_there(false);
        // lsdb.booksInsert(b);7
        System.out.println(lsdb.CategoriesCount());
        java.sql.Date date = java.sql.Date.valueOf("2018-05-15");
        System.out.println(lsdb.CategoriesCountDate(date));

         //  }

     /*   User u = new User();
        u.setTC(145236);
        u.setName("ali");
        u.setSurname("akıcı");
        u.setMail("aliakici@gmail.com");
        u.setPassword("1111");
        u.setPhone(114526);*/

       // lsdb.LibrarianInsert(u);
       // lsdb.UserInsert(u);

        //lsdb.UserDelete(u);

       // lsdb.authorControl();
      //lsdb.dateStatistic();


    }
}
