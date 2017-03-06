import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
public class WorkPlease extends JFrame{

    private JDesktopPane theDesktop;
    File fName;

    public WorkPlease()
    {
        super("Homework 2A");

        theDesktop = new JDesktopPane();
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem exitFile = new JMenuItem("Exit");

        fileMenu.add(openFile);
        fileMenu.add(exitFile);

        JMenu findMenu = new JMenu("Find");
        JMenuItem wordCountItem = new JMenuItem("Word Count");
        JMenuItem statItem = new JMenuItem("File Stats");
        JMenu numMenu = new JMenu("Find Numbers");
        JMenuItem pNumItem = new JMenuItem("Phone Numbers");
        JMenuItem ssNumItem = new JMenuItem("Social Security Numbers");

        findMenu.add(wordCountItem);
        findMenu.add(statItem);
        findMenu.add(numMenu);
        numMenu.add(pNumItem);
        numMenu.add(ssNumItem);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem versionItem = new JMenuItem("Version");

        aboutMenu.add(versionItem);

        exitFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                System.exit(0);
            }
        });

        openFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                File fileName = openFile();
            }
        });

        wordCountItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                JInternalFrame frame = new JInternalFrame("Search for word",true,true,true,true);

                FindPanel fp = new FindPanel();
                frame.add(fp);
                frame.pack();
                theDesktop.add(frame);
                frame.setVisible(true);
            }
        });

        versionItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String message = "Michael Tutone\nVersion 1.0\nOctober 10, 2016";
                JOptionPane.showMessageDialog(new JFrame(),message,"Version Info",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bar.add(fileMenu);
        bar.add(findMenu);
        bar.add(aboutMenu);

        add(theDesktop);
        setJMenuBar(bar);
    }

    public static void main(String args[])
    {
        WorkPlease mt = new WorkPlease();
        mt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mt.setLocationRelativeTo(null);
        mt.setSize(700,500);
        mt.setVisible(true);
    }

    public File openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(this);
        if (result==JFileChooser.CANCEL_OPTION)
            System.exit(0);
        File fileName = fileChooser.getSelectedFile();
        System.out.println(fileName);
        fName = fileName;
        return fileName;
    }

    private class FindPanel extends JPanel
    {
        private JLabel nameLabel;
        private JTextField nameField;
        private JLabel submitLabel;
        private JButton submitButton;
        private JLabel wordLabel;
        private JTextField wordField;
        private JTextArea myArea;

        public FindPanel()
        {
            setLayout(new GridLayout(3,3));
            nameLabel = new JLabel("Find Word");
            nameField = new JTextField(30);
            submitLabel = new JLabel("Click to Submit");
            submitButton = new JButton("Click");
            wordLabel = new JLabel("Word Count");
            wordField = new JTextField(30);

            myArea = new JTextArea();

            Handler myHandler = new Handler();
            submitButton.addActionListener(myHandler);

            add(nameLabel);
            add(nameField);
            add(submitLabel);
            add(submitButton);
            add(wordLabel);
            add(wordField);
        }

        class Handler implements ActionListener
        {
            public void actionPerformed(ActionEvent ae)
            {

                int count = 0;

                try {
                    System.out.println(fName);
                    String word = nameField.getText();
                    BufferedReader br = new BufferedReader(new FileReader(fName));
                    String line;
                    while((line = br.readLine()) != null)
                    {
                        System.out.println(line);
                        String split[]= line.split(" ");
                        for (String s:split)
                            if(word.matches(s))
                            {
                                count++;
                            }
                    }
                    wordField.setText(Integer.toString(count));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(WorkPlease.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(WorkPlease.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}