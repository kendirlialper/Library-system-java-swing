package Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class AdminStatistic {
    private JPanel panel;
    private JButton LCbuton;
    private JButton PCbuton;
    private JPanel butonpanel;
    private JButton backButton;
    static JFrame frame=new JFrame();

    public AdminStatistic() {
        PCbuton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AdminStatisticPC pc=new AdminStatisticPC();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }

            }
        });
        LCbuton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminStatisticLCdate lCdate=new AdminStatisticLCdate();
                lCdate.set6visible();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public void setvisible3(){
        frame.add(new AdminStatistic().panel);
        frame.setVisible(true);
        frame.setSize(600,400);

    }

    public static void main(String[] args) {
        AdminStatistic statistic=new AdminStatistic();
        frame.add(statistic.panel);
        frame.setVisible(true);
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));
    }

}
