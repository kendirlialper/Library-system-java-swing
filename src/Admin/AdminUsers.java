package Admin;

import DB.LSDB;
import DB.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class AdminUsers {
        static JFrame frame=new JFrame();
        private JPanel panel;
        private JPanel deletePanel;
        private JPanel insertPanel;
        private JPanel showPanel;
        private JComboBox kutuphaneciCombo;
        private JButton btnSil;
        private JList showuser;
        private JTextField TCtextField;
        private JTextField nametextField;
        private JTextField surnametextField;
        private JTextField teltextField;
        private JTextField mailtextField;
        private JButton btnEkle;
        private JTextField passtextField;
    private JButton backButton;
    private DefaultListModel listModel1;


        public AdminUsers() throws Exception, SQLException, InstantiationException {
        ListLoad();
        comboLoad();
        btnEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert();
                TCtextField.setText("");
                nametextField.setText("");
                surnametextField.setText("");
                teltextField.setText("");
                mailtextField.setText("");
                passtextField.setText("");
            }
        });
        btnSil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();

            }
        });
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
    }

        public void insert(){
            String TC= TCtextField.getText();
            String name=nametextField.getText();
            String surname=surnametextField.getText();
            float tel= Float.parseFloat(teltextField.getText());
            String mail=mailtextField.getText();
            String pass=passtextField.getText();

            User user=new User();
            user.setTC(TC);
            user.setName(name);
            user.setSurname(surname);
            user.setPhone(tel);
            user.setMail(mail);
            user.setPassword(pass);

            LSDB lsdb=new LSDB();
            try {
                lsdb.UserInsert(user);
                ListLoad();
                comboLoad();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }

        public void delete(){
            String selectedcombobox = (String) kutuphaneciCombo.getSelectedItem();
            String[] selUser = selectedcombobox.split(" ");
            String TCuser = selUser[0];
            LSDB lsdb=new LSDB();
            User user=new User();
            user.setTC(TCuser);

            if (selectedcombobox!=null){
                try {
                    lsdb.UserDelete(user);
                    comboLoad();
                    ListLoad();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        public void comboLoad() throws SQLException, InstantiationException {
        LSDB lsdb=new LSDB();
        kutuphaneciCombo.removeAllItems();
        Object [][] users=userGet(lsdb.listUser());
            for (int i = 0 ; i < users.length ; i++) {
                kutuphaneciCombo.addItem(users[i][0] +"  "+ users[i][1] +"  "+ users[i][2]);
            }
        }

        public void ListLoad() throws SQLException, InstantiationException {

                listModel1= new DefaultListModel();
                LSDB lsdb = new LSDB();
                Object [][] user = userGet(lsdb.listUser());
                for (int i = 0 ; i < user.length ; i++) {
                    listModel1.addElement(user[i][0] +" "+ user[i][1]+" "+user[i][2]);
                }
                showuser.setModel(listModel1);

                //list2.setModel(listModel2);
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

        public void setvisible1() throws Exception {
        try {
            frame.add(new AdminUsers().panel);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
        frame.setSize(600,400);

    }

        public static void main(String[] args) {
        AdminUsers users= null;
        try {
            users = new AdminUsers();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            users.ListLoad();
            users.comboLoad();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        frame.add(users.panel);
        frame.setVisible(true);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));

    }
}
