package Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class AdminMenu {
    private JPanel panel;
    private JButton btnLib;
    private JButton btnUser;
    private JButton btnSta;
    private JPanel pnlButon;
    private JButton logoutButton;
    static JFrame frame=new JFrame();

    public AdminMenu() {


        btnUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminUsers users= null;
                try {
                    users = new AdminUsers();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    users.setvisible1();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnLib.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLibrarians librarians=new AdminLibrarians();
                librarians.setvisible2();
            }
        });
        btnSta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              AdminStatistic adminStatistic = new AdminStatistic();
              adminStatistic.setvisible3();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminMenu.frame.dispose();
            }
        });
    }
  public void setvisible(){
      frame.add(new AdminMenu().panel);
      frame.setVisible(true);
      frame.setSize(500,500);

    }
  public static void main(String[] args) {
        AdminMenu adminMenu=new AdminMenu();
        frame.add(adminMenu.panel);
        frame.setVisible(true);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));
    }
}