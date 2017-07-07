package nn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Controller extends JFrame {
    private static final long serialVersionUID = 2451829341034438685L;
    public static JButton inputButton = new JButton("Send");
    public static JTextArea editTextArea = new JTextArea("");
    public JTextArea textArea = new JTextArea(5, 10);
    JScrollPane scrollPane = new JScrollPane(textArea);
    public JTextArea fitnessarea = new JTextArea(5, 10);
    JScrollPane fitnessPane = new JScrollPane(fitnessarea); 
    private String myString;
    public static boolean show_Net = false;
    public int[] fits;

    public Controller(String title) {
        fits = new int[nn.Bot.Genes.length];
        
        setLayout(new BorderLayout());
        Container c = getContentPane();
        textArea.setEditable(false);
        
        c.add(editTextArea, BorderLayout.SOUTH);
        c.add(inputButton, BorderLayout.CENTER);
        c.add(scrollPane, BorderLayout.WEST);
        c.add(fitnessPane, BorderLayout.EAST);
        
        setSize(new Dimension(300, 500));
        setTitle("Controller - Generation: 0");
        setResizable(false);
        setLocation(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        Controller.inputButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myString = editTextArea.getText();
                if("p".equals(myString)){
                    NN.timer.stop();
                }
                else if("s".equals(myString)){
                    NN.timer.start();
                }
                else if("lg".equals(myString)){
                    NN.list_genes();
                }
                else if("f".equals(myString.substring(0, 1))){
                    NN.timer.stop();
                    int ph = Integer.parseInt(myString.substring(3));
                    NN.ff(NN.bot.generation, ph);
                }
                else if("on".equals(myString)){
                    show_Net = true;
                }
                else if("cn".equals(myString)){
                    show_Net = false;
                }
                else{
                        NN.bot.speed = Integer.parseInt(myString);
                }
                editTextArea.setText("");
            }
        });
    }

    void new_update(String update) {
        textArea.append("\n"+update);
    }
    
    void fit_update(int interval, int fit){
        fits[interval] = fit;
        fitnessarea.setText("");
        for(int i = 0; i < fits.length; i ++){
            fitnessarea.append("Bot: " + Integer.toString(i) + "---" + Integer.toString(fits[i]) + "\n");
        }
    }
}