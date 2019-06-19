package Librarian;

import DB.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddBookDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField bname;
    private JTextField yearofpaint;
    private JComboBox catcombo;
    private JComboBox publishercombo;
    private JTextField authorname;
    private JLabel lbl_book;
    private JLabel lbl_year;
    private JLabel lbl_aname;
    private JLabel lbl_categories;
    private JLabel lbl_publisher;
    private JTextField authorsurname;
    private JLabel lbl_asurname;
    private JButton booklist;
    String bookname = "";
    private int yearofpaintgs;
    private int publisher ;
    private int catogıd ;
    private String aname = "";
    private String asurname =  "";


    public AddBookDialog() throws SQLException, InstantiationException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        LSDB lsdb = new LSDB();
        Object [][] categories = CategoriesGet(lsdb.categoriesList());
        Object [][] author = AuthorGet(lsdb.authorList());
        Object [][] publisherlist = PublisherGet(lsdb.publisherList());

        catcombo.removeAllItems();
        for (int i = 0 ; i < categories.length ; i++) {
            catcombo.addItem(categories[i][0] +"  "+ categories[i][1] );
        }
        publishercombo.removeAllItems();
        for (int i = 0 ; i < publisherlist.length ; i++) {
            publishercombo.addItem(publisherlist[i][0] +"  "+ publisherlist[i][1]);
        }
    //    Connections connections = new Connections();
        //try {
       //     ResultSet set = connections.connection_cek("SELECT * from librarysystem.categories");
          //  ResultSet set1 = connections.connection_cek("SELECT * FROM librarysystem.publisher");
      ///      ResultSet set2 = connections.connection_cek("SELECT * FROM librarysystem.author");
         //   while (set.next())
          //  {
  //              catcombo.addItem(set.getString("name"));
    //        }
     //       while (set1.next())
       //     {
       //         publishercombo.addItem(set1.getString("name"));
        //    }
         //   while (set2.next())
        //    {
      //          yazarcombo.addItem(set2.getString("name") + " " + set2.getString("surname"));
      //      }
     //   } catch (ClassNotFoundException e) {
      //      e.printStackTrace();
      //  } catch (SQLException e) {
       //     e.printStackTrace();
       // }
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
            }
        });

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
        booklist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowAllBooks showAllBooks = new ShowAllBooks();
                showAllBooks.setVisible(true);
            }
        });
    }


    private void onOK() throws SQLException, InstantiationException {
     //   Connections connections1 = new Connections();
        bookname = bname.getText();
        yearofpaintgs = Integer.parseInt(yearofpaint.getText());
        publisher = publishercombo.getSelectedIndex();
        publisher++;
        catogıd = catcombo.getSelectedIndex();
        catogıd++;
        aname = authorname.getText();
        asurname = authorsurname.getText();
        if(bname.getText().equalsIgnoreCase("") && yearofpaint.getText().equalsIgnoreCase("") && authorsurname.getText().equalsIgnoreCase("") && authorname.getText().equalsIgnoreCase("") )
        {
           JOptionPane.showMessageDialog(null,"Boş Alan Bırakılamaz","Hata",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
             try {
                LSDB lsdb = new LSDB();
                Author a = new Author();
                Books b = new Books();
                BooksByAuthor booksByAuthor = new BooksByAuthor();
                a.setName(aname);
                a.setSurname(asurname);
                b.setName(bookname);
                b.setYear_of_printig(yearofpaintgs);
                b.setPublisherID(publisher);
                b.setCategoriesID(catogıd);
                int bookid = lsdb.booksControl(b);
                int authorid = lsdb.authorControl(a);
                if ( bookid == -1 ) {
                    lsdb.booksInsert(b);
                    bookid = lsdb.booksControl(b);
                    if ( authorid == -1  ){
                        lsdb.AuthorInsert(a);
                        authorid = lsdb.authorControl(a);
                        booksByAuthor.setAuthorID(authorid);
                        booksByAuthor.setBookID(bookid);
                        lsdb.BooksByAuothorInsert(booksByAuthor);

                    }
                    else {
                        booksByAuthor.setAuthorID(authorid);
                        booksByAuthor.setBookID(bookid);
                        lsdb.BooksByAuothorInsert(booksByAuthor);
                    }
                    JOptionPane.showMessageDialog(null,"Yeni Kitap Eklendi","Hata",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    if ( authorid == -1  ){
                        lsdb.AuthorInsert(a);
                        authorid = lsdb.authorControl(a);
                        booksByAuthor.setAuthorID(authorid);
                        booksByAuthor.setBookID(bookid);
                        lsdb.BooksByAuothorInsert(booksByAuthor);
                    }
                    else {
                        booksByAuthor.setAuthorID(authorid);
                        booksByAuthor.setBookID(bookid);
                        lsdb.BooksByAuothorInsert(booksByAuthor);
                    }
                    JOptionPane.showMessageDialog(null,"Yazar Eklendi","Bilgi",JOptionPane.WARNING_MESSAGE);

                }

                // connections1.forinsert("INSERT INTO librarysystem.books (name,year_of_printing,publisher_ID,categories_ID,is_there) VALUES ('"+bookname+"','"+yearofpaintgs+"','"+publisher+"','"+catogıd+"','1')");
               // ResultSet resultSet = connections1.connection_cek("SELECT * FROM librarysystem.books WHERE name = '"+bookname+"'");
               // while (resultSet.next())
                //{
                //    int idtut = resultSet.getInt("idBooks");
                //    connections1.forinsert("INSERT INTO librarysystem.booksbyauthor(bookID,authorID) VALUES('"+idtut+"','"+yazaridtut+"')");
               // }

           // } catch (ClassNotFoundException e) {
              //  e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws SQLException, InstantiationException {
        AddBookDialog dialog = new AddBookDialog();
        dialog.setSize(500,500);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public Object [][] AuthorGet(List<Author> authors){
        if (authors == null){
            return null;
        }

        Object [][] result = new Object[authors.size()][5];
        for (int  i = 0 ; i < authors.size()  ; i++){
            result [i][0] = authors.get(i).getIdAuthor();
            result [i][1] = authors.get(i).getName();
            result [i][2] = authors.get(i).getSurname();
            result [i][3] = authors.get(i).getMail();
            result [i][4] = authors.get(i).getPhone();

        }
        return result;

    }

    public Object [][] CategoriesGet(List<Categories> categories){
        if (categories == null){
            return null;
        }

        Object [][] result = new Object[categories.size()][2];
        for (int  i = 0 ; i < categories.size()  ; i++){
            result [i][0] = categories.get(i).getIdCategories();
            result [i][1] = categories.get(i).getName();
            }
        return result;
    }

    public Object [][] PublisherGet(List<Publisher> publishers){
        if (publishers == null){
            return null;
        }

        Object [][] result = new Object[publishers.size()][2];
        for (int  i = 0 ; i < publishers.size()  ; i++){
            result [i][0] = publishers.get(i).getIdPublisher();
            result [i][1] = publishers.get(i).getName();
        }
        return result;
    }

}
