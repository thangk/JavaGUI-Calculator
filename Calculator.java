import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.text.DecimalFormat;
import java.util.Random;
import java.awt.GridLayout;


public class Calculator extends JFrame {

    private Toolkit toolkit = getToolkit();
    private Dimension size = toolkit.getScreenSize(); 

    private static final int WindowsWidth = 400;
    private static final int WindowHeight = 450;

    private static final Font font = new Font("Dialog", Font.PLAIN, 20);
    private static final Font font2 = new Font("Dialog", Font.BOLD, 18);
    private static final Font font3 = new Font("Dialog", Font.PLAIN, 16);

    private String result1 = "0";
    private String expressions = "";
    private String tempExpresion = "";

    private JLabel expressionslabel = new JLabel(expressions);
    private JLabel resultlabel_1 = new JLabel(result1, SwingConstants.RIGHT);
    
    private double termA;
    private double termB;
    private double termResult1;
    private int newEntriesMode = 0;

    private int operateCase = 0;

    /*  case constants:     used for when pressing equal sign
        0   default (none)
        1   percentage
        2   tax
        3   multiplication
        4   division
        5   subtraction
        6   addition
    */
    


    public Calculator() {


        new JFrame();
        this.setTitle("Simple Calculator v1.0");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WindowsWidth, WindowHeight);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocation(size.width/2 - WindowsWidth/2, size.height/2 - WindowHeight/2);

        NumbersPanel numberspanel = new NumbersPanel();
        ResultsWindow resultswindow = new ResultsWindow();
        OperatorsPanel operatorspanel = new OperatorsPanel();
        OtherQuickMaths otherquickmaths = new OtherQuickMaths();

        resultswindow.setBounds(25, 25, 335, 75);
        otherquickmaths.setBounds(25, 110, 235, 50);
        numberspanel.setBounds(25, 170, 235, 200);
        operatorspanel.setBounds(270, 110, 90, 260);

        add(numberspanel);
        add(resultswindow);
        add(operatorspanel);
        add(otherquickmaths);
    }


    public class NumbersPanel extends JPanel {

        public NumbersPanel() {
            // setBackground(Color.YELLOW);
            setLayout(new GridLayout(4,3,5,5));

            String numbers[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".", "+/-"};

            for (int i = 0; i < numbers.length; i++) {
                final int j = i;
                JButton jb = new JButton(numbers[i]);
                jb.setFont(font);
                jb.setFocusable(false);
                jb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (operateCase == 0) {
                            expressions += numbers[j];
                        } else {
                            tempExpresion += numbers[j];
                            expressions += numbers[j];
                            termB = Double.parseDouble(tempExpresion);
                        }
                        expressionslabel.setText(expressions);
                        
                    }
                });
                add(jb);
            }
        }
    }

    public class ResultsWindow extends JPanel {

        public ResultsWindow() {
            setBackground(Color.LIGHT_GRAY);
            setLayout(null);

            
            

            expressionslabel.setFont(font3);
            expressionslabel.setBounds(10, 0, 350, 50);
            expressionslabel.setBackground(Color.ORANGE);
            
            resultlabel_1.setFont(font2);
            resultlabel_1.setBounds(25, 30, 300, 50);
            resultlabel_1.setBackground(Color.RED);

            add(expressionslabel);
            add(resultlabel_1);
        }
    }

    public class OperatorsPanel extends JPanel {

        public OperatorsPanel() {
            // setBackground(Color.GREEN);
            setLayout(new GridLayout(6,0,0,5));

            String operators[] = {"C", "x", "\u00F7", "-", "+", "="};

            for (int i = 0; i < operators.length; i++) {
                final int j = i;
                JButton jb = new JButton(operators[i]);
                jb.setFont(font);
                jb.setFocusable(false);
                jb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        
                        if (e.getSource() == jb && jb.getText() == "C") {
                            result1 = "0";
                            expressions = "";
                            resultlabel_1.setText("0");
                            newEntriesMode = 1;
                        } else if (e.getSource() == jb && jb.getText() == "=") {
                            switch (operateCase) {
                                case 1 : termResult1 = termA * (1-(termB/100.0)); break;
                                case 2 : termResult1 = termA * ((termB/100.0) + 1.0); break;
                                case 3 : termResult1 = termA * termB; break;
                                case 4 : termResult1 = termA / termB; break;
                                case 5 : termResult1 = termA - termB; break;
                                case 6 : termResult1 = termA + termB; break;
                            }
                            if (operateCase == 2) {
                                DecimalFormat df = new DecimalFormat("###.##");
                                resultlabel_1.setText(df.format(termResult1));
                            } else {
                                resultlabel_1.setText(String.valueOf(termResult1));
                            }
                            
                            operateCase = 0;
                            expressions = "";
                            tempExpresion = "";

                        } else if (e.getSource() == jb && jb.getText() == "x") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 3;
                            expressions += " x ";
                        } else if (e.getSource() == jb && jb.getText() == "\u00F7") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 4;
                            expressions += " \u00F7 ";
                        } else if (e.getSource() == jb && jb.getText() == "-") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 5;
                            expressions += " - ";
                        } else if (e.getSource() == jb && jb.getText() == "+") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 6;
                            expressions += " + ";
                        } else {
                            expressions += operators[j];
                        }
                        expressionslabel.setText(expressions);
                        
                    }
                });
                add(jb);
            }
        }
    }

    public class OtherQuickMaths extends JPanel {

        public OtherQuickMaths() {
            // setBackground(Color.BLUE);
            setLayout(new GridLayout(1,3,5,0));

            String oqm[] = {"x\u00B2", "%off", "Tax"};

            for (int i = 0; i < oqm.length; i++) {
                final int j = i;
                JButton jb = new JButton(oqm[i]);
                jb.setFont(font);
                jb.setFocusable(false);
                jb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (newEntriesMode == 1) {

                            // resets
                            termResult1 = 0;
                            termA = 0;
                            termB = 0;
                            operateCase = 0;
                            // expressions = "";
                            // tempExpresion = "";
                            newEntriesMode = 0;
                        }

                        System.out.println("Clicked: " + oqm[j]);

                        if (e.getSource() == jb && jb.getText() == "x\u00B2") {
                            double evalNum = Double.parseDouble(expressions);
                            evalNum *= evalNum;
                            resultlabel_1.setText(String.valueOf(evalNum));
                            expressions = "";
                        } else if (e.getSource() == jb && jb.getText() == "%off") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 1;
                            expressions += " % ";
                        } else if (e.getSource() == jb && jb.getText() == "Tax") {
                            termA = Double.parseDouble(expressions);
                            operateCase = 2;
                            expressions += " %Tax ";
                        } else {
                            expressions += oqm[j];
                            
                        }
                        expressionslabel.setText(expressions);
                    }
                });
                add(jb);
            }
        }
    }


    public static void main(String[] args) {
        new Calculator();
    }
}
