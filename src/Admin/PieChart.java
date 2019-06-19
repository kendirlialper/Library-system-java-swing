package Admin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PieChart extends JPanel {
    private ArrayList values,colors;
    public PieChart(ArrayList<Double> values, ArrayList colors){
        this.values=values;
        this.colors=colors;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int lastPoint = -270;
        Double angle=0.0;
        Double sum=0.0;
        for (int i = 0; i<values.size(); i++){


            sum+= (Double)values.get(i);
        }
        for (int i=0;i<values.size();i++){
            graphics2D.setColor((Color)colors.get(i));
            Double a= (Double) values.get(i);
            angle = (a/sum)*365;
            graphics2D.fillArc(0,0, width, width, lastPoint, -angle.intValue() );
            lastPoint += -angle.intValue();
        }




    }
}