package nn;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class NN {
    static int population_size = 20;
    static int maze_h;
    static int maze_w;
    static Bot bot = new Bot(population_size);
    
    static String[][] maze;
    static int tick = 0;
    static String[][] sighted = new String[3][3];
    
    static JLabel[] labels = new JLabel[15];
    static Maze Maze_frame;
    static Timer timer = new Timer(100, new MyTimerActionListener());
    
    static boolean done = false;
    
    public static void main(String[] args)
    {
        System.out.println("Beginning Simulation...");
        System.out.println("Generating "+population_size+" Genes For Population...");
        Generate_maze();
        Maze_frame = new Maze(maze_h, maze_w, bot.y, bot.x, maze, bot.dirx, bot.diry);
        timer.start();
    }
    
    static void Generate_maze(){
        File file = new File("\\Users\\Charles\\workspace\\Maze\\src\\nn\\setup.txt");
        Scanner input1 = null;
        Scanner input2 = null;
        try {
            input1 = new Scanner(file);
            input2 = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NN.class.getName()).log(Level.SEVERE, null, ex);
        }

        while(input1.hasNextLine()){
            String line = input1.nextLine();
            maze_w = line.length();
            maze_h++;
        }
        
        maze = new String[maze_h][maze_w];
        for(int a = 0; a < maze_h; a++){
            String line = input2.nextLine();
            maze[a] = line.split("");
        }
        
        System.out.println("Generating A Size " +maze_h+ "x" +maze_w+ " Maze...");
        
        for(int a = 0; a < maze_h; a++){
            for(int b = 0; b < maze_w; b++){
                maze[a][b] = maze[a][b+1];
            }
        }
    }

    static void list_genes() {
        for(String g : Bot.Genes){
            System.out.println(g);
        }
    }
    
    static void ff(int generation, int gens){
        int ph = generation + gens;
        while(bot.generation < ph && !done){
            NN.bot.turn(NN.maze);
            NN.Maze_frame.moveTo(NN.bot.y, NN.bot.x, NN.maze, NN.bot.current_genes, NN.bot.tu, NN.bot.dirx, NN.bot.diry);
            NN.timer.setDelay(NN.bot.speed);
        }
    }

    static void maze_Completed() {
        System.out.println("Maze has been completed!");
    }

    }
class MyTimerActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        NN.bot.turn(NN.maze);
        NN.Maze_frame.moveTo(NN.bot.y, NN.bot.x, NN.maze, NN.bot.current_genes, NN.bot.tu, NN.bot.dirx, NN.bot.diry);
        NN.timer.setDelay(NN.bot.speed);
    }
}