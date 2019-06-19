package Admin;

import DB.LSDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminStatisticPC {
    static JFrame frame=new JFrame();
    private JPanel panel;
    PieChart pie;
    JLabel legend;

    public AdminStatisticPC() throws SQLException, InstantiationException {
        try {
            drawPC();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    public void drawPC() throws SQLException, InstantiationException {
        frame = new JFrame("Pie Chart");
        frame.setSize(400,400);
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LSDB lsdb=new LSDB();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.magenta);
        colors.add(Color.green);
        colors.add(Color.blue);
        colors.add(Color.yellow);
        pie = new PieChart(lsdb.CategoriesCount(),colors);
        legend = new JLabel();
        // You can use HTML in JLabel
        legend.setText("<html> <font color = 'fuchsia'> Dünya Edebiyatı </font> <br/>" +
                "<font color = 'green'>Eğitim-Bilgisayar</font> <br/>" +
                "<font color = 'blue'> Roman </font> <br/>" +
                "<font color = 'yellow'> Türk Edebiyatı </font> <br/> </html>");
        //Define the position and font of the text in the JLabel legend
        legend.setHorizontalAlignment(SwingConstants.LEADING);
        legend.setVerticalAlignment(SwingConstants.TOP);
        legend.setFont( new Font("serif",Font.BOLD,18));

        //Define the layout and add components to the frame
        GridLayout layout = new GridLayout(0,2);
        frame.setLayout(layout);
        frame.add(legend);
        frame.add(pie);
        frame.setVisible(true);
    }

 /* public void setvisible3(){
        frame.add(new Admin.AdminStatisticPC().panel);

        frame.setVisible(true);
        frame.setSize(400,400);

  }  */

    public static void main(String[] args) throws SQLException, InstantiationException {
       AdminStatisticPC statistic=new AdminStatisticPC();
       //statistic.drawPC();
      /*frame.add(statistic.panel);
        frame.add(statistic.legend);
        frame.add(statistic.pie);
        frame.setVisible(true);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_ACTIVATED));*/
    }
}
