package Librarian;

import DB.LSDB;
import DB.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserGiris extends JFrame{
    private JPanel panel1;
    private JTextField username;
    private JButton girişButton;
    private JTextField password;
    private JLabel lbl_mail;
    private JLabel lbl_password;

    public UserGiris() {
        girişButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gir();

            }
        });
    }
    private void gir() {

        if(username.getText().equalsIgnoreCase("") && password.getText().equalsIgnoreCase(""))
        {
            JOptionPane.showMessageDialog(null,"Boş alan bırakılamaz","Hata",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          //  Connections connections = new Connections();
            try {
                LSDB lsdb = new LSDB();
                User u = lsdb.librarianControl(username.getText() , password.getText());
               // ResultSet set = connections.connection_cek("select * from librarysystem.user WHERE name = '"+username.getText()+"' AND password = '"+password.getText()+"'");
                if( u != null) {
                    dispose();
                    LibinSystem libinSystem = new LibinSystem();
                    libinSystem.setvisible1();
                    username.setText("");
                    password.setText("");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Hatalı Giris yaptınız","Uyarı",JOptionPane.ERROR_MESSAGE);
                    username.setText("");
                    password.setText("");
                }

            // } catch (ClassNotFoundException e) {
            //    e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        UserGiris userGiris = new UserGiris();
        JFrame jFrame = new JFrame();
        jFrame.add(userGiris.panel1);
        jFrame.setVisible(true);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
