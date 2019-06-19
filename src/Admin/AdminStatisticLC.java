package Admin;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;


public class AdminStatisticLC extends JFrame {

    private JPanel panel;
    static JFrame frame = new AdminStatisticLC();
    public AdminStatisticLC() {
        setLayout(new BorderLayout());
        add(new LineChart(), BorderLayout.CENTER);
    }

    public AdminStatisticLC(Date date){
        setLayout(new BorderLayout());
        add(new LineChart(), BorderLayout.CENTER);
    }



    public void set5visible(){

        //frame.add(new AdminStatisticLC().panel);
        frame.setVisible(true);
        frame.setSize(500,500);

    }
    public static void main(String[] args) {
      //  frame.add(new AdminStatisticLC().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Line Chart");
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
