package nn;

import java.util.ArrayList;
import java.util.Random;

public class Bot {
    static ArrayList<Connection> connections = new ArrayList<>();
    public static ArrayList<Neuron> neurons = new ArrayList<>();
    int x = 1;
    int y = 1;
    int diry = 0;
    int dirx = 1;
    public String[] around = new String[4];
    static String[] Genes;
    static Controller Controller_frame;
    static Node Node_frame;
    static int current_genes = 0;
    static Random rand = new Random();
    int speed = 100;
    int[][] not_moving = new int[100][100];
    int[] fit;
    int generation = 0;
    int tu = 0;
    int[] previousfit;
    int f = 0;
    boolean mutated = false;
    
    Bot(int population_size){
        load_basics();
        Generate_genes(population_size);
        Controller_frame = new Controller("Controller");
        Node_frame = new Node();
        for(int e = 0; e < 100; e++){
            for(int b = 0; b < 100; b++){
                not_moving[e][b] = 0;
            }
        }
        fit = new int[population_size];
        for(int b = 0; b < 15; b++)
        {
            fit[b] = 1;
        }
        previousfit = new int[population_size];
        for(int b = 0; b < 15; b++)
        {
            previousfit[b] = 1;
        }
        Node_frame.new_bot();
    }
    
    static void load_basics() {
        neurons.add(new Neuron("out0", 1, 0, false, 1, 9));
        neurons.add(new Neuron("out1", 1, 0, false, 2, 9));
        neurons.add(new Neuron("out2", 1, 0, false, 3, 9));
        neurons.add(new Neuron("out3", 1, 0, false, 4, 9));
        for(int i = 0; i < 4; i++){
            neurons.add(new Neuron("inn"+Integer.toString(i), 0, 1, true, i, 1));
        }
        neurons.add(new Neuron("bas1", 0, 1, true, 4, 1));
    }
    
    static void Generate_genes(int n){
        Genes = new String[n];
        for(int i = 0; i < n; i++){
            Genes[i]="";
        }
    }

    static void load_Genes(String bot_gene) {
        String gene = Genes[current_genes];
        for(int i = 0; i < (gene).length()/6; i++){
            if("1".equals(gene.substring(i*6, i*6+1))){
                if("1".equals(gene.substring(i*6+1, i*6+2))){
                    connections.add(new Connection("c", 1, Integer.parseInt(gene.substring(i*6+2, i*6+4)), Integer.parseInt(gene.substring(i*6+4, i*6+6))));
                }
                else{
                    connections.add(new Connection("c", -1, Integer.parseInt(gene.substring(i*6+2, i*6+4)), Integer.parseInt(gene.substring(i*6+4, i*6+6))));
                }
            }
            else{
                if("0".equals(gene.substring(i*6+2, i*6+3))){
                    neurons.add(new Neuron("mid", Integer.parseInt(gene.substring(i*6+1, i*6+2)), -1, false, Integer.parseInt(gene.substring(i*6+4, i*6+5)), Integer.parseInt(gene.substring(i*6+5, i*6+6))));
                }
                else{
                    neurons.add(new Neuron("mid", Integer.parseInt(gene.substring(i*6+1, i*6+2)), 1, false, Integer.parseInt(gene.substring(i*6+4, i*6+5)), Integer.parseInt(gene.substring(i*6+5, i*6+6))));
                }
            }
        }
    }
    
    void reset() {
        tu = 0;
        x = 1;
        y = 1;
        diry = 0;
        dirx = 1;
        not_moving = new int[100][100];
        neurons.clear();
        connections.clear();
        load_basics();
    }

    void turn(String[][] maze) {
        update_around(maze);
        choice(around);
        use_choice(maze);
        check_moving();
        reload();
    }

    private void update_around(String[][] maze) {
        around[0] = maze[y+diry][x+dirx];
        around[1] = maze[y+-dirx][x+diry];
        around[2] = maze[y+dirx][x+-diry];
        around[3] = maze[y+(-diry)][x+(-dirx)];
    }

    private void choice(String[] around) {
        for(Neuron n : neurons){
            if("inn".equals(n.name.substring(0, 3))){
                int interval = Integer.parseInt(n.name.substring(3));
                if("W".equals(around[interval])){
                    n.activation_type = -1;
                }
                else{
                    n.activation_type = 1;
                }
            }
        }
        for(int i = 0; i < 3; i++){
            for(Connection c : connections){  
                try {
                    if(neurons.get(c.in).active && !c.done){
                    neurons.get(c.out).add_one(c.connection_type, neurons.get(c.in).activation_type);
                    c.done = true;
                }
                } catch (IndexOutOfBoundsException ex) {
                    System.out.print(neurons.size()+" ");
                    System.out.print(c.in+" ");
                    System.out.print(c.out);
                    System.out.println();
                }
            }
        }
        if(nn.Controller.show_Net)
            Node_frame.update();
    }

    private void use_choice(String[][] maze) {
        for(Neuron n : neurons){
            String sub = (n.name.substring(0, 3));
            if("out".equals(sub)){
                if(n.active){
                    sub = n.name.substring(3);
                    switch (sub){
                        case "0":
                            if(!"W".equals(maze[y+diry][x+dirx])){
                                y+=diry;
                                x+=dirx;
                            }
                            break;
                        case "1":
                            if(diry == 0 && dirx == 1){
                                diry = -1;
                                dirx = 0;
                            }
                            else if(diry == 0 && dirx == -1){
                                diry = 1;
                                dirx = 0;
                            }
                            else if(diry == 1 && dirx == 0){
                                diry = 0;
                                dirx = 1;
                            }
                            else if(diry == -1 && dirx == 0){
                                diry = 0;
                                dirx = -1;
                            }
                            break;
                        case "2":
                            if(diry == 0 && dirx == 1){
                                diry = 1;
                                dirx = 0;
                            }
                            else if(diry == 0 && dirx == -1){
                                diry = -1;
                                dirx = 0;
                            }
                            else if(diry == 1 && dirx == 0){
                                diry = 0;
                                dirx = -1;
                            }
                            else if(diry == -1 && dirx == 0){
                                diry = 0;
                                dirx = 1;
                            }
                            break;
                        case "3":
                            if(!"W".equals(maze[y+(-diry)][x+(-dirx)])){
                                y+=(-diry);
                                x+=(-dirx);
                            }
                            break;
                    }
                }
            }
        }
    }
    
    private void check_moving() {
        tu++;
        if(tu >= 5000)
            NN.done = true;
        else
            NN.done = false;

        if("E".equals(NN.maze[y][x])){
            NN.maze_Completed();
            NN.done = true;
            NN.timer.stop();
        }
        
        if(not_moving[y][x] == 0){
            f+=1;
        }
        
        not_moving[y][x]++;
        
        if(not_moving[y][x] >= 10 || NN.done){
            next_bot();
        }
    }
    
    private void reload() {
        for(Neuron n : neurons){
            if(!"inn".equals(n.name.substring(0, 3)) && !"bas".equals(n.name.substring(0, 3))){
                n.active = false;
                n.level = 0;
            }
        }
        for(Connection c : connections){
            if(c.done)
                c.done = false;
        }
    }

    private void mutate() {
        if(rand.nextInt(100) <= 4){
            String mutation = "";
            if(rand.nextInt(100) >= 25){
                mutation+="1";
                if(rand.nextInt(2) == 0){
                    mutation+="1";
                    int firstconnection = rand.nextInt(neurons.size()-1);
                    int secondconnection = rand.nextInt(neurons.size()-1);
                    while("out".equals(neurons.get(firstconnection).name.substring(0, 3))){
                        firstconnection = rand.nextInt(neurons.size()-1);
                    }
                    while("inn".equals(neurons.get(secondconnection).name.substring(0, 3)) || "bas".equals(neurons.get(secondconnection).name.substring(0, 3)) || secondconnection == firstconnection){
                        secondconnection = rand.nextInt(neurons.size()-1);
                    }
                    String c1 = Integer.toString(firstconnection);
                    String c2 = Integer.toString(secondconnection);

                    if(c1.length()<2){
                        c1 = "0" + c1;
                    }
                    if(c2.length()<2){
                        c2 = "0" + c2;
                    }
                    mutation+=c1;
                    mutation+=c2;
                }
                else{
                    mutation+="0";
                    int firstconnection = rand.nextInt(neurons.size()-1);
                    int secondconnection = rand.nextInt(neurons.size()-1);
                    while("out".equals(neurons.get(firstconnection).name.substring(0, 3))){
                        firstconnection = rand.nextInt(neurons.size()-1);
                    }
                    while("inn".equals(neurons.get(secondconnection).name.substring(0, 3)) || "bas".equals(neurons.get(secondconnection).name.substring(0, 3)) || secondconnection == firstconnection){
                        secondconnection = rand.nextInt(neurons.size()-1);
                    }
                    String c1 = Integer.toString(firstconnection);
                    String c2 = Integer.toString(secondconnection);

                    if(c1.length()<2){
                        c1 = "0" + c1;
                    }
                    if(c2.length()<2){
                        c2 = "0" + c2;
                    }
                    mutation+=c1;
                    mutation+=c2;
                }
            }
            else{
                mutation+="0";
                mutation+=Integer.toString(rand.nextInt(4));
                if(rand.nextInt(2) == 0){
                    mutation+="0";
                }
                else{
                    mutation+="1";
                }
                mutation+="0";
                mutation+=Integer.toString(rand.nextInt(7)+2);
                mutation+=Integer.toString(rand.nextInt(7)+2);
            }
            Genes[current_genes]+=mutation;
            Controller_frame.new_update(Integer.toString(current_genes) + "-" + mutation);
            mutated = true;
        }
    }

    private void next_bot() {
        fit[current_genes] = f;
        f = 0;
        
        if(mutated)
            check_mutations();
        
        Controller_frame.fit_update(current_genes, fit[current_genes]);
        current_genes++;
        
        if(current_genes == Genes.length){
            generation++;
            current_genes = 0;
            Controller_frame.setTitle("Controller - Generation: "+Integer.toString(generation));
            int total = 0;
            for(int f : fit){
                total+=f;
                System.out.println((total/fit.length));
            }
            System.out.println();
            for(int i = 0; i < fit.length; i++){
                previousfit[i] = fit[i];
            }
            fit = new int[NN.population_size];
        }
        
        reset();
        mutate();
        load_Genes(Genes[current_genes]);
        Node_frame.new_bot();
    }
    
    private void check_mutations() {
        if(previousfit[current_genes] >= fit[current_genes]){
            Genes[current_genes] = Genes[current_genes].substring(0, Genes[current_genes].length()-6);
        }
        mutated = false;
    }
}