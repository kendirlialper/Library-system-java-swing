package Librarian;

import DB.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BarrowBooksDialog extends JDialog {

    private JPanel contentPane;
    private JButton buttonbarrow;
    private JButton getback;
    private JList list1;
    private JList list2;
    private JComboBox comboBox1;
    private JButton bilgiAlButton;
    private JButton geriButton;
    private JButton cezalarıGörüntüleButton;
    private DefaultListModel listModel1;
    private DefaultListModel listModel2;
    int counter =0 ;

    public BarrowBooksDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonbarrow);
        comboBox1.setVisible(true);
        buttonbarrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Barrowit();
                    listyükle();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        listyükle();
        getback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GetBackit();

            }
        });

        cezalarıGörüntüleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showthefine();
            }
        });
        bilgiAlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getinfo();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        geriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GetBackit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    // barrowkısmı
    private void Barrowit() throws ClassNotFoundException, SQLException, InstantiationException {
       // Connections connections = new Connections();
        String selected = (String) list1.getSelectedValue();
        String[] tutbook = selected.split(" ");
        String selectedcombobox = (String) comboBox1.getSelectedItem();
        String[] tutuser = selectedcombobox.split(" ");
        String iduser = tutuser[0];
        String idtutbook = tutbook[0];
        int bookid = Integer.parseInt(idtutbook);

        System.out.println(selected.toString());
        if(selected != null && selectedcombobox != null)
        {
          //  LocalDate localDate = LocalDate.now();
          //  Date today = new Date();
          //  Calendar c = Calendar.getInstance();
          //  today = c.getTime();
            Books b = new Books();
            b.setIdBook(bookid);
            b.setIs_there(false);
            Transaction t = new Transaction();
            t.setBookID(bookid);
            t.setUserID(iduser);
            Debts d = new Debts();
            d.setBookID(b.getIdBook());
            d.setTC(t.getUserID());
            d.setDebts(0);
            LSDB lsdb = new LSDB();
            lsdb.books_thereis(b);
            lsdb.TransactionInsert(t);
            lsdb.DebtInsert(d);
            //connections.forinsert("UPDATE librarysystem.books SET is_there = 0 WHERE name = '"+selected+"'");
             //LocalDate localDate = LocalDate.now();
           // LocalDate yarın = localDate.plus(10,ChronoUnit.DAYS);
           // ResultSet set1 = connections.connection_cek("SELECT idBooks FROM librarysystem.books WHERE name = '"+selected+"'");
           // while(set1.next())
           // {
            //    String t = set1.getString("idBooks");
             //    tut2 = Integer.parseInt(t);
           // }
            //connections.forinsert("INSERT INTO librarysystem.transaction (dateofdesposit,deliverydate,userID,bookID)
            // VALUES ('"+localDate+"','"+yarın+"','"+idinttut+"','"+tut2+"')");
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Kitap ve Kişi Seçiniz","Uyarı",JOptionPane.WARNING_MESSAGE);
        }
        listyükle();
    }
    // liste yenileme için
    public void listyükle() {
        //Connections connections = new Connections();
        try {
            listModel1 = new DefaultListModel();
            listModel2 = new DefaultListModel();
          //  ResultSet set = connections.connection_cek("SELECT * FROM librarysystem.books WHERE is_there = 1");
          //  ResultSet set1 = connections.connection_cek("SELECT * FROM librarysystem.books WHERE is_there = 0");
          //  ResultSet set3 = connections.connection_cek("SELECT * FROM librarysystem.user");
          //  while (set.next())
            //{
               // listModel1.addElement(set.getInt("idBooks");
                //listModel1.addElement(set.getString("name"));
        //    }

          //  while (set1.next())
         //   {
           //     listModel2.addElement(set1.getString("name"));
           // }
            //while (set3.next())
           // {
                LSDB lsdb = new LSDB();
                Object [][] user = userGet(lsdb.listUser());
                Object [][] book1 = booksGet(lsdb.booksList(true));
                Object [][] book0 = booksGet(lsdb.booksList(false));
                //comboBox1.addItem(set3.getString("name") + " " + set3.getString("surname") + " " + set3.getString("TC"));
                comboBox1.removeAllItems();
                for (int i = 0 ; i < user.length ; i++) {
                    comboBox1.addItem(user[i][0] +"  "+ user[i][1] +"  "+ user[i][2] );
                }
            for (int i = 0 ; i < book1.length ; i++) {
                listModel1.addElement(book1[i][4] +" "+ book1[i][0]);
            }
            if( book0 != null){
            for (int i = 0 ; i < book0.length ; i++) {
                listModel2.addElement(book0[i][4] +" "+ book0[i][0]);
            }
                }
          //  }
            list1.setModel(listModel1);
            list2.setModel(listModel2);

      //  } catch (ClassNotFoundException e) {
        //    e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e )
        {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    } // button info

    private void getinfo() throws SQLException, InstantiationException {
       // Connections connections = new Connections();

        String selected = (String) list2.getSelectedValue();
        String[] tutbook = selected.split(" ");
        String idtutbook = tutbook[0];
        String bookname = tutbook[2];
        int bookid = Integer.parseInt(idtutbook);
        LSDB lsdb = new LSDB();
        Transaction t =  lsdb.TrancationInfo(bookid);
        if(list2.getSelectedValue() != null)
        {
        String userid = t.getUserID();
        Date dateofdesposit = t.getD_of_desposit();
        Date deliverydate = t.getDelivery_date();

           // try {
              // ResultSet set= connections.connection_cek("SELECT idBooks FROM librarysystem.books WHERE name = '"+kitap_name_tut+"'");
             //   while (set.next())
              //  {
               //     String sttut = set.getString("idBooks");
                //    inttut = Integer.parseInt(sttut);
                  //  System.out.println(" " + inttut);
                //}
                //ResultSet set2 = connections.connection_cek("SELECT * FROM librarysystem.transaction WHERE bookID = '"+inttut+"'");
               // while (set2.next())
                //{
                    //date1 = set2.getDate("dateofdesposit");
                   // date2 = set2.getDate("deliverydate");
                   // userid = set2.getInt("userID");
                    //date3 =((java.sql.Date) date1).toLocalDate();
                  //  date4 = ((java.sql.Date) date2).toLocalDate();

                //}
              //} catch (ClassNotFoundException e) {
              //  e.printStackTrace();
           // } catch (SQLException e) {
              //  e.printStackTrace();
            //} catch (InstantiationException e) {
             //   e.printStackTrace();
            //}
         //  JOptionPane.showMessageDialog(null,"Kitap Adı :'"+kitap_name_tut+"'  Alım Tarihi : '"+date3+"' Getirim Tarihi : '"+date4+"' Alıcının ID :'"+userid+"' ","Bilgi",JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null,"  Alım Tarihi : '"+  dateofdesposit +"' Getirim Tarihi : '"+deliverydate+"' Alıcının ID :'"+userid+"' ","Bilgi",JOptionPane.INFORMATION_MESSAGE);

        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ödünç Verilmişlerden Seçinizi","Uyarı",JOptionPane.INFORMATION_MESSAGE);
        }


    }

    // ödünç verileni geri almak için
    private void GetBackit() {
        String selected = (String) list2.getSelectedValue();
        String[] tutbook = selected.split(" ");
        String idtutbook = tutbook[0];
        int bookid = Integer.parseInt(idtutbook);


        if(list2.getSelectedValue() != null)
        {
            //int id_of_book = 0 ;
            //Connections connections = new Connections();
            //String kitap_adi = (String) list2.getSelectedValue();
            try {
                LSDB lsdb = new LSDB();
                Books b = new Books();
                Debts d = new Debts();
                b.setIdBook(bookid);
                b.setIs_there(true);
                Transaction t = new Transaction();
                t.setBookID(bookid);
                t.setUserID(lsdb.TrancationInfo(bookid).getUserID());
                t.setD_of_desposit(lsdb.TrancationInfo(bookid).getD_of_desposit());
                d.setTC(lsdb.TrancationInfo(bookid).getUserID());
                d.setBookID(b.getIdBook());
                lsdb.books_thereis(b);
                lsdb.TransactionDelete(t);
                lsdb.DebtsDelete(d);
              //  connections.forinsert("UPDATE librarysystem.books SET is_there = 1 WHERE name = '"+kitap_adi+"'");
               // ResultSet set1 = connections.connection_cek("SELECT idBooks FROM librarysystem.books WHERE name ='"+kitap_adi+"'");
                //while (set1.next())
              //  {
                //    id_of_book = set1.getInt("idBooks");
               // }
               // connections.forinsert("DELETE FROM librarysystem.transaction WHERE bookID = '"+id_of_book+"'");
                System.out.println("Silindi");
           // } catch (ClassNotFoundException e) {
            //    e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Rafa Koymak İçin Kitap Seçiniz","Uyarı",JOptionPane.INFORMATION_MESSAGE);
        }
        listyükle();
    }

    private void showthefine() {
        ShowTheFind showTheFind = new ShowTheFind();
        showTheFind.setVisible(true);
    }

    public static void main(String[] args) {
        BarrowBooksDialog dialog = new BarrowBooksDialog();
        dialog.setSize(500,500);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public Object [][] userGet(List<User> user){
        if (user == null){
            return null;
        }

        Object [][] sonuc = new Object[user.size()][7];
        for (int  i = 0 ; i < user.size()  ; i++){
            sonuc[i][0] = user.get(i).getTC();
            sonuc[i][1] = user.get(i).getName();
            sonuc[i][2] = user.get(i).getSurname();
            sonuc[i][3] = user.get(i).getMail();
            sonuc[i][4] = user.get(i).getPassword();
            sonuc[i][5] = user.get(i).getPhone();
            sonuc[i][6] = user.get(i).getAuthorityID();
        }
        return sonuc;

    }

    public Object [][] booksGet(List<Books> book){
        if (book == null){
            return null;
        }

        Object [][] result = new Object[book.size()][5];
        for (int  i = 0 ; i < book.size()  ; i++){
            result [i][0] = book.get(i).getName();
            result [i][1] = book.get(i).getCategoriesID();
            result [i][2] = book.get(i).getPublisherID();
            result [i][3] = book.get(i).getYear_of_printig();
            result [i][4] = book.get(i).getIdBook();

        }
        return result;

    }

    public Object [] transactionGet(List<Transaction> transactions){
        if (transactions == null){
            return null;
        }

        Object [] result = new Object [5];
        for (int  i = 0 ; i < transactions.size()  ; i++){
            result [0] = transactions.get(i).getD_of_desposit();
            result [1] = transactions.get(i).getDelivery_date();
            result [2] = transactions.get(i).getBookID();
            result [4] = transactions.get(i).getUserID();

        }
        return result;

    }
}
