package Database;

import java.sql.*;
import java.util.ArrayList;
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
            u.setTC(rs.getString("TC"));
            u.setName(rs.getString("name"));
            u.setSurname(rs.getString("surname"));
        }
        return u;
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
    //tüm kitapları ceker
    public List<String> booksListAll( ) throws InstantiationException, SQLException{
        String sql = " select books.name as Name , categories.name as Categories, publisher.name as Publisher\n" +
                "from librarysystem.books  JOIN librarysystem.categories \n" +
                "ON books.categories_ID = categories.idcategories \n" +
                "JOIN librarysystem.publisher\n" +
                "ON books.publisher_ID = publisher.idpublisher  " +
                "Order By  books.name ;";
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
            c.setName(rs.getString("Categories"));
            p.setName(rs.getString("Publisher"));
            list.add(b.getName());
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
    public List<String> booksListUser(String userID) throws InstantiationException, SQLException{
        String sql = "select  books.name , user.name , user.surname , transaction.dateofdesposit from librarysystem.books , librarysystem.transaction , librarysystem.user\n" +
                "where transaction.bookID = books.idBooks and transaction.userID =? \n" +
                " and transaction.dateofdesposit <= curdate()and transaction.userID = user.TC   ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, userID);
        ResultSet rs = pstmt.executeQuery();
        List <String> list = null;
        if(rs.next()){
            list = new ArrayList<String>();

        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Transaction t = new Transaction();
            User u = new User();
            b.setName(rs.getString("books.name"));
            u.setName(rs.getString("user.name"));
            u.setName(rs.getString("user.surname"));
            t.setD_of_desposit(rs.getDate("transaction.dateofdesposit"));
            list.add(b.getName());
            list.add(u.getName());
            list.add(u.getSurname());
            list.add(t.getD_of_desposit().toString());

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    //kitapları kategorisine göre sıralıyor
    public List<String> booksListCategories() throws InstantiationException, SQLException{
        String sql = " Select books.name ,  publisher.name ,  categories.name from librarysystem.books , librarysystem.publisher , librarysystem.categories\n" +
                " where books.categories_ID = categories.idcategories and books.publisher_ID = publisher.idpublisher  Order By  categories.name ; ";
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
            Publisher p  = new Publisher();
            b.setName(rs.getString("books.name"));
            c.setName(rs.getString("categories.name"));
            p.setName(rs.getString("publisher.name"));
            list.add(b.getName());
            list.add(c.getName());
            list.add(p.getName());

            if( list.isEmpty() == true){
                System.out.print("liste bos");
            }

        }

        pstmt.close();
        rs.close();
        return list;
    }

    //kitapları yayınevine göre sıralıyor
    public List<String> booksListPublisher() throws InstantiationException, SQLException {
        String sql = "  Select books.name ,  publisher.name ,  categories.name from librarysystem.books , librarysystem.publisher , librarysystem.categories\n" +
                " where books.categories_ID = categories.idcategories and books.publisher_ID = publisher.idpublisher  Order By  publisher.name;  ";
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
        while ( rs.next()) {
            Books b = new Books();
            Categories c = new Categories();
            Publisher p = new Publisher();
            b.setName(rs.getString("books.name"));
            c.setName(rs.getString("categories.name"));
            p.setName(rs.getString("publisher.name"));
            list.add(b.getName());
            list.add(c.getName());
            list.add(p.getName());

            if (list.isEmpty() == true) {
                System.out.print("liste bos");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    // kitapların içinde verilen parametreye göer arama yapıyor
    public List<String> booksListSearch(String index) throws InstantiationException, SQLException{
        String sql = "  select books.name as Name , categories.name as Categories, publisher.name as Publisher\n" +
                "from librarysystem.books  JOIN librarysystem.categories \n" +
                "ON books.categories_ID = categories.idcategories \n" +
                "JOIN librarysystem.publisher\n" +
                "ON books.publisher_ID = publisher.idpublisher \n" +
                "and books.name like ?; ";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 , "%"+index+"%" );
        ResultSet rs = pstmt.executeQuery();
        List<String> list = null;
        if(rs.next()){
            list = new ArrayList<>();
        }
        rs.beforeFirst();
        while ( rs.next()){
            Books b = new Books();
            Categories c = new Categories();
            Publisher p = new Publisher();
            b.setName(rs.getString("Name"));
            c.setName(rs.getString("Categories"));
            p.setName(rs.getString("Publisher"));
            list.add(b.getName());
            list.add(c.getName());
            list.add(p.getName());

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
        String sql = "Insert Into librarysystem.books ( name , year_of_printing , publisher_ID , categories_ID , is_there )\n" +
                "value (? , ? , ? , ? , ? ) ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1 , b.getName());
        pstmt.setInt(2 ,  b.getYear_of_printig());
        pstmt.setInt(3 ,  b.getPublisherID());
        pstmt.setInt(4 ,  b.getCategoriesID());
        pstmt.setBoolean(5 ,  b.isIs_there());

        int result = pstmt.executeUpdate();
        pstmt.close();

        return (result >0);
    }

    //girilen isimde yazar var ise onun idsini cekiyor
    public List<Author> authorControl(Author a) throws InstantiationException, SQLException{
        String sql = "Select idAuthor from author where author.name = ? and author.surname = ? ;";
        if (connection  == null ){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, a.getName());
        pstmt.setString(2, a.getSurname());
        ResultSet rs = pstmt.executeQuery();
        List <Author> list = null;
        if(rs.next()){
            list = new ArrayList<Author>();
            list.add(a);
            for(int i = 0 ; i<list.size() ; i++){
                System.out.println(list.get(i));
            }

        }
        if( list.isEmpty() == true){
            System.out.println("liste bos");
            return null;
        }
        else{
            System.out.print("yazar ID \t");
            System.out.print(rs.getString(1)  +"\t");
            return list;
        }

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
                "values ( CurDate() , dateofdesposit + interval 30 day , ? , ? ) ;";

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

    public List<String> BooksByAouthorList() throws SQLException, InstantiationException {
        String sql = "select author.name, author.surname , books.name  from  booksbyauthor " +
                "INNER JOIN books ON booksbyauthor.bookID = books.idBooks " +
                "INNER  JOIN author ON booksbyauthor.authorID = author.idAuthor ORDER BY books.name";
        if (connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List <String> list = null;
        if(rs.next()){
            list = new ArrayList<String>();

        }
        rs.beforeFirst();
        while ( rs.next()) {
            Books b = new Books();
            Author a = new Author();
            b.setName(rs.getString("books.name"));
            a.setName(rs.getString("author.name"));
            a.setSurname(rs.getString("author.surname"));
            list.add(a.getName());
            list.add(a.getSurname());
            list.add(b.getName());

            if (list.isEmpty() == true) {
                System.out.print("liste bos");
            }
        }

        pstmt.close();
        rs.close();
        return list;
    }

    public boolean  TransactionDelete(Transaction t) throws SQLException, InstantiationException {
        String sql = "delete from librarysystem.transaction where TC =? and bookID=?; " ;
        if (connection == null){
            Open();
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, t.getUserID());
        pstmt.setInt(2 , t.getBookID());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;

    }

    //verien tc deki kişiyi siliyor
    public boolean UserDelete(User u) throws SQLException{
        String sql = "delete from librarysystem.user where TC =?; " ;
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, u.getTC());
        boolean result = (pstmt.executeUpdate() > 0 );
        pstmt.close();
        return result;
    }


            ////////////////////  TEST METODU //////////////////////////////
    public static void main(String args[]) throws InstantiationException, SQLException {
        LSDB lsdb = new LSDB();
        lsdb.booksListAll();
    }
}
