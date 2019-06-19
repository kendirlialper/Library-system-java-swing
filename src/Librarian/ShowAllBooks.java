package Librarian;

import DB.LSDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShowAllBooks extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList list1;
    private DefaultListModel listModel;

    public ShowAllBooks() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        listyenile();
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
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void listyenile()
    {
       // Connections connections = new Connections();
        int publisherid = 0 ;
        int catagoriesid = 0 ;
        int idBooks = 0 ;
        String publishername = "";
        String categoriesname = "";
        int yazarid = 0;
        String yazarname = "";
        String yazarsurname = "";
        listModel = new DefaultListModel();
        try {
            LSDB lsdb = new LSDB();
            List<String> list = lsdb.booksListAll();
            for (int i = 0 ; i < list.size() ; i+=4) {
              listModel.addElement("Kitap AD =>" + list.get(i) + " " + " Yazım Tarihi => " + list.get(i+1) + " " + "Yayımcı Şirket => " + " " + list.get(i+3)+ " Katagorisi => " + list.get(i+2));
            }
            list1.setModel(listModel);
           /* ResultSet setdondür = connections.connection_cek("SELECT * FROM librarysystem.books");
            while (setdondür.next())
            {
                publisherid = setdondür.getInt("publisher_ID");
                catagoriesid = setdondür.getInt("categories_ID");
                idBooks = setdondür.getInt("idBooks");
                ResultSet publisherset = connections.connection_cek("SELECT name FROM librarysystem.publisher WHERE idpublisher ='"+publisherid+"'");
                ResultSet categoriesset = connections.connection_cek("SELECT name FROM librarysystem.categories WHERE idcategories ='"+catagoriesid+"'");
                ResultSet booksbyauthor = connections.connection_cek("SELECT authorID FROM librarysystem.booksbyauthor WHERE bookID ='"+idBooks+"'");
                while (publisherset.next())
                {  publishername = publisherset.getString("name");}
                while (categoriesset.next())
                { categoriesname = categoriesset.getString("name");}
                while (booksbyauthor.next())
                { yazarid = booksbyauthor.getInt("authorID");}
                ResultSet authornames =connections.connection_cek("SELECT * FROM librarysystem.author WHERE idAuthor ='"+yazarid+"'");
                while (authornames.next())
                {yazarname = authornames.getString("name");
                 yazarsurname = authornames.getString("surname");
                }

                listModel.addElement("Kitap AD =>" + setdondür.getString("name") + " " + "Yazarı => " + yazarname + " " + yazarsurname + " Yazım Tarihi => " + setdondür.getInt("year_of_printing") + " " + "Yayımcı Şirket => "+ " " +  publishername + " Katagorisi => " + categoriesname);
                list1.setModel(listModel);

*/

            } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        }


    }

    public static void main(String[] args) {
        ShowAllBooks dialog = new ShowAllBooks();
        dialog.pack();
        dialog.setSize(500,500);
        dialog.setVisible(true);
        System.exit(0);
    }
}
