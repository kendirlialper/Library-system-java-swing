package Admin;

import Admin.Methods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class AdminStatisticLCdate {
    private JPanel panel;
    private JTextField datetextField;
    private JButton showButton;
    static JFrame frame=new JFrame();
    static Date date;

    public AdminStatisticLCdate() {

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AdminStatisticLC lc=new AdminStatisticLC();
                lc.set5visible();
                getDate();
                datetextField.setText("");
            }
        });
    }

    public Date getDate() {
        date = Date.valueOf(datetextField.getText());
        return date;
    }

    public void set6visible(){
        frame.add(new AdminStatisticLCdate().panel);
        frame.setVisible(true);
        frame.setSize(500,300);

    }

    public static void main(String[] args) {
        AdminStatisticLCdate statistic=new AdminStatisticLCdate();
        frame.add(statistic.panel);
        frame.setVisible(true);
        frame.setSize(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));
    }
}


