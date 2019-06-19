package Librarian;

import DB.Author;
import DB.Books;
import DB.BooksByAuthor;
import DB.LSDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.print.Book;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LibinSystem {
    private JPanel panel1;
    private JButton kitapEkleButton;
    private JButton dosyaYoluIleEkleButton;
    private JButton exacuteButton;
    private JButton çıkışButton;
    private initfordata initfordata;
    static JFrame jFrame = new JFrame();

    public LibinSystem()  {
        kitapEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookDialog addBookDialog = null;
                try {
                    addBookDialog = new AddBookDialog();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                }
                addBookDialog.setVisible(true);
            }
        });
        çıkışButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                  LibinSystem.jFrame.dispose();

            }
        });
        dosyaYoluIleEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File thisdir = new File(System.getProperty("user.dir"));
                JFileChooser chooser = new JFileChooser(thisdir);
                int returnval = chooser.showOpenDialog(null);
                if(returnval == JFileChooser.APPROVE_OPTION)
                {
                    File file = chooser.getSelectedFile();

                    initfordata = new initfordata(file);

                    try {
                        System.out.println();
                        List<String> list = initfordata.fileRead(file);
                        String [] book = new String[0];
                        LSDB lsdb = new LSDB();
                        Books b = new Books();
                        Author a = new Author();
                        BooksByAuthor booksByAuthor = new BooksByAuthor();
                        for (int j = 0 ; j < list.size() ; j++){
                         book  = list.get(j).split("->");

                        for(int i = 0 ; i< book.length ; i+=6) {
                            b.setName(book[i]);
                            b.setCategoriesID(Integer.parseInt(book[i + 1]));
                            b.setPublisherID(Integer.parseInt(book[i+2]));
                            b.setYear_of_printig(Integer.parseInt(book[i+3]));
                            a.setName(book[i+4]);
                            a.setSurname(book[i+5]);
                            int bookid = lsdb.booksControl(b);
                            int authorid = lsdb.authorControl(a);
                            if (bookid == -1) {
                                lsdb.booksInsert(b);
                                bookid = lsdb.booksControl(b);
                                if (authorid == -1) {
                                    lsdb.AuthorInsert(a);
                                    authorid = lsdb.authorControl(a);
                                    booksByAuthor.setAuthorID(authorid);
                                    booksByAuthor.setBookID(bookid);
                                    lsdb.BooksByAuothorInsert(booksByAuthor);

                                } else {
                                    booksByAuthor.setAuthorID(authorid);
                                    booksByAuthor.setBookID(bookid);
                                    lsdb.BooksByAuothorInsert(booksByAuthor);
                                }

                            } else {
                                if (authorid == -1) {
                                    lsdb.AuthorInsert(a);
                                    authorid = lsdb.authorControl(a);
                                    booksByAuthor.setAuthorID(authorid);
                                    booksByAuthor.setBookID(bookid);
                                    lsdb.BooksByAuothorInsert(booksByAuthor);
                                } else {
                                    booksByAuthor.setAuthorID(authorid);
                                    booksByAuthor.setBookID(bookid);
                                    lsdb.BooksByAuothorInsert(booksByAuthor);
                                }

                            }
                        }
                    }
                        JOptionPane.showMessageDialog(null, "Ekleme İşlemi Tamamlandı", "Bilgi", JOptionPane.WARNING_MESSAGE);

                } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }


                }
            }
        });
        exacuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarrowBooksDialog barrowBooksDialog = new BarrowBooksDialog();
                barrowBooksDialog.setVisible(true);
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }

    public void setvisible1()    {
        jFrame.add(new LibinSystem().panel1);
        jFrame.setVisible(true);
        jFrame.setSize(500,500);
    }

    public static void main(String[] args) {

        LibinSystem libinSystem = new LibinSystem();
        jFrame.add(libinSystem.panel1);
        jFrame.setVisible(true);
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.dispatchEvent(new WindowEvent(jFrame,WindowEvent.WINDOW_ACTIVATED));
    }

}

class initfordata {
    public initfordata(File gelenfile ) {
        converttodata(gelenfile);
    }

    public void converttodata(File file )    {
        ArrayList gelenler = new ArrayList();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String iterator = bufferedReader.readLine();
            while (iterator != null)
            {
                System.out.println(iterator.toString());
                iterator = bufferedReader.readLine();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static List<String> fileRead(File file) throws  IOException{

        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<String>();
        while( scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
         scanner.close();
        return list;

    }
}



