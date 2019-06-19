package Librarian;

import DB.Books;
import DB.Debts;
import DB.LSDB;
import DB.Transaction;
import Librarian.BarrowBooksDialog;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class ShowTheFind extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private DefaultListModel listModel;


    public ShowTheFind() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    payit();
                    listyükle();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        listyükle();
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onCancel();

            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void payit() throws SQLException, InstantiationException {
        if (list1.getSelectedValue() != null)
        {
           int input =JOptionPane.showConfirmDialog(null,"Emin Misin","Uyarı",JOptionPane.YES_NO_OPTION);
           if(input == 0 )
           {
               String[] bilgi = list1.getSelectedValue().toString().split("/");
               String TC = bilgi[3];
               int bookid = Integer.parseInt(bilgi[0]);
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
               JOptionPane.showMessageDialog(null,"Silindi","Uyarı",JOptionPane.WARNING_MESSAGE);
           }
           else if(input == 1 )
           {
               // no durumu

           }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Ödeneceği Kişinin Bilgilerini Seçiniz","Uyarı",JOptionPane.WARNING_MESSAGE);
        }

    }

    private void onCancel() {
        // add your code here if necessary
      dispose();

    }

    private void listyükle()    {
        Date date_of_desposit = null;
        Date delivery_date = null;
        Date localDate = null;
        String name ;
        String surname;
        String bookid ;
        String bookname;
        String TC;
        float debt;
        listModel = new DefaultListModel();
        //int tut ;
        //int tut2;
        //int userID ;
        //String gelenname = null;
        //String gelensurname = null;
        //Connections connections = new Connections();
        try {
               LSDB lsdb = new LSDB();
                List list2 = lsdb.DebtList();
            if(list2 != null) {
                for (int j = 0; j < list2.size(); j += 6) {
                    String bname = (String) list2.get(j);
                    int idbook = (int) list2.get(j + 1);
                    String u_TC = (String) list2.get(j + 2);
                    float debts = (float) list2.get(j + 3);
                    String u_name = (String) list2.get(j + 4);
                    String u_surname = (String) list2.get(j + 5);
                    LocalDate now = LocalDate.now();
                    delivery_date = lsdb.TrancationInfo(idbook).getDelivery_date();
                    float newdebt = 0;
                    if( now.getDayOfYear() > delivery_date.toLocalDate().getDayOfYear() ){
                    int fark = (now.getDayOfYear() - delivery_date.toLocalDate().getDayOfYear());
                       newdebt = (2 * fark);
                    }
                    if ( newdebt == debts){
                        listModel.addElement(idbook +  "/  " + bname + "/  " + debts + "/  " + u_TC + "/  " + u_name + "/  " + u_surname);
                    }
                    else {
                        Debts d = new Debts();
                        d.setTC(u_TC);
                        d.setDebts(newdebt);
                        d.setBookID(idbook);
                        lsdb.DebtUpdate(d);
                        listModel.addElement(idbook + "/  " + bname + "/  " + newdebt + "/  " + u_TC + "/  " + u_name + "/  " + u_surname);
                    }


                }
            }
            else {
                //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy - mm - dd");
                List<String> list = lsdb.TrancationList();
                for (int i = 0 ; i < list.size() ; i+=7){
                    bookid = list.get(i);
                    TC = list.get(i+1);
                    name = list.get(i+2);
                    surname = list.get(i+3);
                    date_of_desposit = Date.valueOf(list.get(i+4));
                    delivery_date = Date.valueOf(list.get(i+5));
                    bookname = list.get(i+6);
                    LocalDate now = LocalDate.now();
                    int id = Integer.parseInt(bookid);
                    if( now.getDayOfYear() > delivery_date.toLocalDate().getDayOfYear() ){
                    int fark = (now.getDayOfYear() - delivery_date.toLocalDate().getDayOfYear());
                    debt = (2 * fark);
                    }
                    else{
                        debt = 0 ;
                    }
                    Debts d = new Debts();
                    d.setDebts(debt);
                    d.setTC(TC);
                    d.setBookID(id);
                    lsdb.DebtInsert(d);
                    listModel.addElement(bookid + "/" + bookname + "/" + debt + "/" + TC + "/" + name + "/" + surname);

                }
            }

            list1.setModel(listModel);
          } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        }

        // ResultSet transaction_result_set = connections.connection_cek("SELECT * FROM librarysystem.transaction");
            //while (transaction_result_set.next())
         //   {
             //   date = transaction_result_set.getDate("deliverydate"); // teslim tarihini aldık
              //  tut2 = transaction_result_set.getInt("bookID"); // bookid i çektik
               // userID = transaction_result_set.getInt("userID"); // useridiyi çektik
             //   localDate = ((java.sql.Date) date).toLocalDate(); // localdate e çevirdik
              //  tut = localDate.compareTo(LocalDate.now()); // eğer teslim tarihini bugünün tarihi geçmiş ise cezai işlem uygulanacağı için -1 döndüreni alacağız
               // if (tut == -1) //
               // {
                //    ResultSet set1 = connections.connection_cek("SELECT * FROM librarysystem.books WHERE idBooks = '"+tut2+"'");
                 //   ResultSet set2 = connections.connection_cek("SELECT * FROM librarysystem.user WHERE idUser = '"+userID+"'");
                  //  while (set2.next())
                    //{
                   //     gelenname = set2.getString("name");
                      //  gelensurname = set2.getString("surname");
               //     }
                 //   while (set1.next())
                   // {
                 //       ceza_tutari = (2*(-1*(localDate.getDayOfYear() - LocalDate.now().getDayOfYear())));
                   //     listModel.addElement(set1.getString("name")+ "-->" + ceza_tutari + " " + " tl ücret isteniyor" + "||" +  " Alan Kişi Bilgilier --> " + userID+ " , " +gelenname+ " , " + gelensurname);
                  //  }
               // }
           // }
           // list1.setModel(listModel);
        //} catch (ClassNotFoundException e) {
           // e.printStackTrace();
       // } catch (SQLException e) {
          //  e.printStackTrace();
        //}
    public static void main(String[] args) {
        ShowTheFind dialog = new ShowTheFind();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setSize(500,500);
        System.exit(0);
    }
}
