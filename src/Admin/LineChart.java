package Admin;

import DB.LSDB;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LineChart extends JLabel {
    Double yy1=0.0,yy2=0.0,yy3=0.0,yy4=0.0;
    int y1=0,y2=0,y3=0,y4=0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(20,230,380,230); //x axis
        g.drawLine(370,223,380,230);
        g.drawLine(370,237,380,230);

        g.drawLine(40,20,40,250); //y axis
        g.drawLine(40,20,30,30);
        g.drawLine(40,20,50,30);

        //x divided
        g.drawLine(92,225,92,235);
        g.drawLine(164,225,164,235);
        g.drawLine(236,225,236,235);
        g.drawLine(308,225,308,235);
        //y divided
        g.drawLine(35,66,45, 66);
        g.drawLine(35,112,45,112);
        g.drawLine(35,158,45,158);
        g.drawLine(35,204,45,204);

        // g.setColor(Color.blue);
        g.drawString("Dünya",75,250);
        g.drawString("Edebiyatı",75,262);

        g.drawString("Eğitim",147,250);
        g.drawString("Bilgisayar",147,262);

        g.drawString("Roman",219,250);

        g.drawString("Türk",297,250);
        g.drawString("Edebiyatı",297,262);

        g.drawString("20",20,70);
        g.drawString("15",20,116);
        g.drawString("10",20,162);
        g.drawString("5",25,208);

        //draw axis labels
        g.setColor(Color.black);
        g.drawString("0",33,240);
        g.drawString("X",365,247);
        g.drawString("Y",50,30);

        AdminStatisticLCdate lCdate=new AdminStatisticLCdate();
        LSDB lsdb=new LSDB();
        try {
            yy1=lsdb.CategoriesCountDate(AdminStatisticLCdate.date).get(0);
            yy2=lsdb.CategoriesCountDate(AdminStatisticLCdate.date).get(1);
            yy3=lsdb.CategoriesCountDate(AdminStatisticLCdate.date).get(2);
            yy4=lsdb.CategoriesCountDate(AdminStatisticLCdate.date).get(3);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        //Dünya edebiyatı değerini belirleme
        if (yy1==0.0){
            y1=229;
        }
        else if (yy1 < 5) {
            y1=219;
        }
        else if (yy1 == 5.0){
            y1=208;
        }
        else if (5<yy1&&yy1<10){
            y1=185;
        }
        else if (yy1==10.0){
            y1=162;
        }
        else if (10<yy1&&yy1<15){
            y1=139;
        }
        else if (yy1==15.0){
            y1=116;
        }
        else if (15<yy1&&yy1<20){
            y1=93;
        }
        else if (yy1==20.0){
            y1=70;
        }
        else if (yy1>20){
            y1=47;
        }
        //Eğitim bilgisayar değerini belirleme
        if (yy2==0.0){
            y2=229;
        }
        else if (yy2 < 5) {
            y2=219;
        }
        else if (yy2 == 5.0){
            y2=208;
        }
        else if (5<yy2&&yy2<10){
            y2=185;
        }
        else if (yy2==10.0){
            y2=162;
        }
        else if (10<yy2&&yy2<15){
            y2=139;
        }
        else if (yy2==15.0){
            y2=116;
        }
        else if (15<yy2&&yy2<20){
            y2=93;
        }
        else if (yy2==20.0){
            y2=70;
        }
        else if (yy2>20){
            y2=47;
        }

        //Roman değerinin belirlenmesi
        if (yy3==0.0){
            y3=229;
        }
        else if (yy3 < 5) {
            y3=219;
        }
        else if (yy3 == 5.0){
            y3=208;
        }
        else if (5<yy3&&yy3<10){
            y3=185;
        }
        else if (yy3==10.0){
            y3=162;
        }
        else if (10<yy3&&yy3<15){
            y3=139;
        }
        else if (yy3==15.0){
            y3=116;
        }
        else if (15<yy3&&yy3<20){
            y3=93;
        }
        else if (yy3==20.0){
            y3=70;
        }
        else if (yy3>20){
            y3=47;
        }
        //Türk edebiyatının değerinin belirlenmesi
        if (yy4==0.0){
            y4=229;
        }
        else if (yy4 < 5) {
            y4=219;
        }
        else if (yy4 == 5.0){
            y4=208;
        }
        else if (5<yy4&&yy4<10){
            y4=185;
        }
        else if (yy4==10.0){
            y4=162;
        }
        else if (10<yy4&&yy4<15){
            y4=139;
        }
        else if (yy4==15.0){
            y4=116;
        }
        else if (15<yy4&&yy4<20){
            y4=93;
        }
        else if (yy4==20.0){
            y4=70;
        }
        else if (yy4>20){
            y4=47;
        }

        g.setColor(Color.red);

        g.drawLine(92,y1,164,y2);
        g.drawLine(164,y2,236,y3);
        g.drawLine(236,y3,308,y4);
    }
}
