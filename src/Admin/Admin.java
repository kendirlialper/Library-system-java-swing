package Admin;

import DB.LSDB;
import Admin.Methods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Admin {
    private JTextField txtUser;
    private JTextField txtPassword;
    private JButton btnEnter;
    private JLabel lblUser;
    private JLabel lblPassword;
    public JPanel panel;
    private JPanel kpanel;
    public boolean isOk=false;

    public Admin() {


        btnEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LSDB lsdb=new LSDB();
                btnEnter=new JButton();

                String userName=txtUser.getText();
                String password=txtPassword.getText();

                try {

                    if (lsdb.adminControl(userName,password) != null ){
                        AdminMenu adminMenu=new AdminMenu();
                        adminMenu.setvisible();
                    }
                    else{
                        Methods.showMesage("Yanlış kullanıcı adı veya şifre!!!","HATA");
                    }
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }


            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Admin admin=new Admin();
                JFrame frame=new JFrame();
                frame.setSize(600,400);
                frame.add(admin.panel);
                frame.setTitle("Admin Giriş Sayfası");
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        });
    }

}