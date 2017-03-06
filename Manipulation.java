//Bejong Yang
//INSY 4306

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manipulation extends JFrame
{
    private JMenuBar menu;
    private JMenu file;
    private JMenu find;
    private JMenu about;
    private JMenuItem open;
    private JMenuItem exit;
    private JMenuItem wordCount;
    private JMenuItem fileStat;
    private JMenuItem findNum;
    private JMenuItem phoneNum;
    private JMenuItem socialNum;
    private JMenuItem version;
    private JFileChooser chooseFile;
    private JDesktopPane desktop;
    private ArrayList<String> phoneNumbers = new ArrayList<>();
    private ArrayList<String> socialNumbers = new ArrayList<>();
    private StringBuilder phoneString = new StringBuilder();
    private StringBuilder socialString = new StringBuilder();
    private int numOfWords;
    private int numOfChars;
    private int numOfLines;
    private String fullString;


    //Main Method
    public static void main(String [] args)
    {
        Manipulation w = new Manipulation();
        w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        w.setLocationRelativeTo(null);
        w.setSize(600,400);
        w.setVisible(true);
    }

    public Manipulation()
    {
        super("Manipulation");
        desktop = new JDesktopPane();
        setContentPane(desktop);


        ActionListener ear = new listener();
        chooseFile = new JFileChooser();

        menu = new JMenuBar();
        file = new JMenu("File");
        find = new JMenu("Find");
        about = new JMenu("About");

        menu.add(file);
        menu.add(find);
        menu.add(about);

        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");
        file.add(open);
        file.add(exit);
        open.addActionListener(ear);
        exit.addActionListener(ear);

        wordCount = new JMenuItem("Word Count");
        fileStat = new JMenuItem("File Stats");
        findNum = new JMenu("Find Numbers");
        phoneNum = new JMenuItem("Phone Numbers");
        socialNum = new JMenuItem("Social Security Numbers");
        find.add(wordCount);
        find.add(fileStat);
        find.add(findNum);
        findNum.add(phoneNum);
        findNum.add(socialNum);
        wordCount.addActionListener(ear);
        phoneNum.addActionListener(ear);
        socialNum.addActionListener(ear);
        fileStat.addActionListener(ear);

        version = new JMenuItem("Version");
        about.add(version);
        version.addActionListener(ear);

        super.setJMenuBar(menu);
    }

    //Class Listener also know as giant logic block
    private class listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
           if(e.getSource() == exit)
           {
               System.exit(0);
           }
           else if(e.getSource() == open)
           {
               int result = chooseFile.showOpenDialog(Manipulation.this);
               if(result == JFileChooser.APPROVE_OPTION)
               {
                   File selectedFile = chooseFile.getSelectedFile();
                   try
                   {
                       //Read in file
                       BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                       String currentString;
                       numOfLines = 0;

                       //Read file line by line
                       while((currentString = br.readLine()) != null)
                       {
                           fullString += currentString;
                           numOfLines++;
                       }
                       //Phone Number Regex
                       Matcher m = Pattern.compile("([(][0-9]{3}[)]\\s[0-9]{3}[-][0-9]{4})")
                               .matcher(fullString);
                       //Social Security Regex
                       Matcher s = Pattern.compile("([0-9]{3}[-][0-9]{2}[-][0-9]{4})")
                               .matcher(fullString);

                       //Phone Number Regex Loops
                       while(m.find())
                       {
                           phoneNumbers.add(m.group());
                       }

                       for(String finalString: phoneNumbers)
                       {
                           phoneString.append(finalString + "\n");
                       }

                       //Social Security Regex Loops
                       while(s.find())
                       {
                           socialNumbers.add(s.group());
                       }

                       for(String finalString: socialNumbers)
                       {
                           socialString.append(finalString + "\n");
                       }

                       //////Word Count and File Stat\\\\\
                       numOfWords = fullString.split(" ").length;
                       numOfChars = fullString.length();
                   }
                   catch (FileNotFoundException fnfe)
                   {
                       fnfe.printStackTrace();
                   }
                   catch (IOException ioe)
                   {
                       ioe.printStackTrace();
                   }
               }
           }
           else if(e.getSource() == wordCount)
           {
               JInternalFrame frame = new JInternalFrame("Find Words", true, true, true, true);
               FindWordPanel fp = new FindWordPanel();

               frame.add(fp);
               frame.pack();
               desktop.add(frame);
               frame.setVisible(true);
           }
            //Find Phone
           else if(e.getSource() == phoneNum)
           {
               JInternalFrame frame = new JInternalFrame("Phone Numbers", true, true, true, true);
               BlankPanel b = new BlankPanel();
               b.setText(phoneString.toString());
               frame.add(b);
               frame.pack();
               desktop.add(frame);
               frame.setVisible(true);
           }
           else if(e.getSource() == socialNum)
           {
               JInternalFrame frame = new JInternalFrame("Social Security", true, true, true, true);
               BlankPanel b = new BlankPanel();
               b.setText(socialString.toString());
               frame.add(b);
               frame.pack();
               desktop.add(frame);
               frame.setVisible(true);
           }
           else if(e.getSource() == fileStat)
           {
               String message = "Number of words: " + numOfWords + "\n Number of characters: " + numOfChars + "\n Number of lines: " + numOfLines;
               JOptionPane.showMessageDialog(null, message);
           }
           else if(e.getSource() == version)
           {
               String message = "Version 1.0.1 \n" +  "10/10/2016 \n" + "Bejong Yang \n";
               JOptionPane.showMessageDialog(null, message);
           }
        }
    }

    //Find Word Panel
     class FindWordPanel extends JPanel
     {
         private JLabel nameLabel;
         private JTextField nameField;
         private JLabel submitLabel;
         private JButton submitButton;
         private JLabel countLabel;
         private JTextField countField;

        public FindWordPanel()
        {
            setLayout(new GridLayout(3,3));
            nameLabel = new JLabel(" Find Words");
            nameField = new JTextField(15);
            submitLabel = new JLabel( " Click to find");
            submitButton = new JButton("Search");
            countField = new JTextField();
            countLabel = new JLabel("Word count");

            Handler myHandler = new Handler();

            submitButton.addActionListener(myHandler);

            add(nameLabel);
            add(nameField);
            add(countLabel);
            add(countField);
            add(submitLabel);
            add(submitButton);
        }

        class Handler implements ActionListener
        {
            public void actionPerformed(ActionEvent ae)
            {
                String findThisWord;
                findThisWord = nameField.getText();
                int findCount = 0;
                int lastIndex = 0;

                while(lastIndex != -1)
                {
                    lastIndex = fullString.indexOf(findThisWord, lastIndex);

                    if(lastIndex != -1)
                    {
                        findCount++;
                        lastIndex += findThisWord.length();
                    }
                }
                countField.setText(String.valueOf(findCount));
            }
        }
    }

    //Blank Text Area
    class BlankPanel extends JPanel
    {
        private String mString;
        JTextArea text;

        public BlankPanel()
        {
            setLayout(new FlowLayout());
            text = new JTextArea();
            text.setEditable(false);
            add(text);
        }

        public void setText(String s)
        {
            mString = s;
            text.append(mString);
        }
    }
}
