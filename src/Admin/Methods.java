package Admin;

import javax.swing.*;

public class Methods {

    public static void showMesage(String mesage,String mesageType) {
        if (mesageType.equals("HATA")) {
        JOptionPane.showMessageDialog(new JFrame(),mesage,"HATA",JOptionPane.ERROR_MESSAGE);
        }
        else if (mesageType.equals("BİLGİ")){
            JOptionPane.showMessageDialog(new JFrame(),mesage,"BİLGİ",JOptionPane.INFORMATION_MESSAGE);
        }
        else if (mesageType.equals("UYARI")){
            JOptionPane.showMessageDialog(new JFrame(),mesage,"UYARI",JOptionPane.WARNING_MESSAGE);
        }
    }


}
