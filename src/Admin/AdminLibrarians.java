package Admin;

import DB.LSDB;
import DB.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class AdminLibrarians {
    static JFrame frame=new JFrame();
    private JPanel panel;
    private JComboBox libcomboBox;
    private JButton deletelibButton;
    private JTextField TClibtextField;
    private JTextField namelibtextField;
    private JTextField surnametextField;
    private JTextField passlibField;
    private JTextField teltextField;
    private JTextField maillibtextField;
    private JButton insertlibButton;
    private JList libJList;
    private JButton backButton;
    private DefaultListModel listModel1;

    public AdminLibrarians() {
        try {
            ListLoad();
            libcomboLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        insertlibButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                libinsert();
                TClibtextField.setText("");
                namelibtextField.setText("");
                surnametextField.setText("");
                teltextField.setText("");
                maillibtextField.setText("");
                passlibField.setText("");
            }
        });
        deletelibButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                libdelete();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
    public void libdelete(){
        String selectedcombobox = (String) libcomboBox.getSelectedItem();
        String[] selUser = selectedcombobox.split(" ");
        String TCuser = selUser[0];
        LSDB lsdb=new LSDB();
        User user=new User();
        user.setTC(TCuser);

        if (selectedcombobox!=null){
            try {
                lsdb.LibrarianDelete(user);
                libcomboLoad();
                ListLoad();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
    public void libinsert(){
        String TC= TClibtextField.getText();
        String name=namelibtextField.getText();
        String surname=surnametextField.getText();
        float tel= Float.parseFloat(teltextField.getText());
        String mail=maillibtextField.getText();
        String pass=passlibField.getText();

        User user=new User();
        user.setTC(TC);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(tel);
        user.setMail(mail);
        user.setPassword(pass);

        LSDB lsdb=new LSDB();
        try {
            lsdb.LibrarianInsert(user);
            ListLoad();
            libcomboLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
    public void ListLoad() throws SQLException, InstantiationException {

        listModel1= new DefaultListModel();
        LSDB lsdb = new LSDB();
        Object [][] user = usersGet(lsdb.listLibrarian());
        for (int i = 0 ; i < user.length ; i++) {
            listModel1.addElement(user[i][0] +" "+ user[i][1]+" "+user[i][2]);
        }
        libJList.setModel(listModel1);

        //list2.setModel(listModel2);
    }

    public void libcomboLoad() throws SQLException, InstantiationException {
        LSDB lsdb=new LSDB();
       libcomboBox.removeAllItems();
        Object [][] users=usersGet(lsdb.listLibrarian());
        for (int i = 0 ; i < users.length ; i++) {
            libcomboBox.addItem(users[i][0] +"  "+ users[i][1] +"  "+ users[i][2]);
        }
    }
    public Object [][] usersGet(List<User> user){
        if (user == null){
            return null;
        }

        Object [][] sonuc = new Object[user.size()][7];
        for (int  j = 0 ; j < user.size()  ; j++){
            sonuc[j][0] = user.get(j).getTC();
            sonuc[j][1] = user.get(j).getName();
            sonuc[j][2] = user.get(j).getSurname();
            sonuc[j][3] = user.get(j).getMail();
            sonuc[j][4] = user.get(j).getPassword();
            sonuc[j][5] = user.get(j).getPhone();
            sonuc[j][6] = user.get(j).getAuthorityID();
        }
        return sonuc;

    }

    public void setvisible2(){
        frame.add(new AdminLibrarians().panel);
        frame.setVisible(true);
        frame.setSize(600,400);

    }

    public static void main(String[] args) {
        AdminLibrarians librarians=new AdminLibrarians();
        try {
            librarians.libcomboLoad();
            librarians.ListLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        frame.add(librarians.panel);
        frame.setVisible(true);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));



    }
}
