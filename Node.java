package nn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class DrawPanel extends JPanel {

    private void doDrawing(Graphics g) {
        Graphics2D p = (Graphics2D) g;
        for(int i = 0; i < Node.ners.length; i++){
            if(Node.ners[i][2] == 1){
                if(Node.ners[i][3] == 1)
                    p.setColor(Color.red);
                else if(Node.ners[i][3] == 0)
                    p.setColor(Color.black);
                else
                    p.setColor(Color.blue);
                p.drawRect((Node.ners[i][1]*40)+20, (Node.ners[i][0]*35), 20, 20);
            }
            else{
                p.setColor(Color.black);
                p.fillRect((Node.ners[i][1]*40)+20, (Node.ners[i][0]*35), 20, 20);
            }
        }
        
        for(int i = 0; i < Node.cons.length; i++){
            if(Node.cons[i][2] == 1){
                p.setColor(Color.red);
            }
            else{
                p.setColor(Color.blue);
            }
            p.drawLine((Node.ners[Node.cons[i][0]][1]*40)+30, (Node.ners[Node.cons[i][0]][0]*35)+20, (Node.ners[Node.cons[i][1]][1]*40)+30, (Node.ners[Node.cons[i][1]][0]*35));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
    }
}

public class Node{
    
    DrawPanel draw;
    JFrame panel;
    static int[][] cons;
    static int[][] ners;
    
    public Node() {
        draw = new DrawPanel();
        panel = new JFrame();
        panel.add(draw);

        panel.setSize(400, 400);
        panel.setTitle("Nodes");
        panel.setVisible(true);
        panel.setLocation(80, 200);
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void new_bot(){
        cons = new int[Bot.connections.size()][3];
        ners = new int[Bot.neurons.size()][4];
        for(int i = 0; i < Bot.connections.size(); i++){
            cons[i][0] = Bot.connections.get(i).in;
            cons[i][1] = Bot.connections.get(i).out;
            cons[i][2] = Bot.connections.get(i).connection_type;
        }
        for(int i = 0; i < Bot.neurons.size(); i++){
            ners[i][0] = Bot.neurons.get(i).y;
            ners[i][1] = Bot.neurons.get(i).x;
            ners[i][2] = (Bot.neurons.get(i).active) ? 1 : 0;
            ners[i][3] = Bot.neurons.get(i).activation_type;
        }
    }

    void update() {
        for(int i = 0; i < Bot.neurons.size(); i++){
            ners[i][2] = (Bot.neurons.get(i).active) ? 1 : 0;
            ners[i][3] = Bot.neurons.get(i).activation_type;
        }
        draw.repaint();
    }
}