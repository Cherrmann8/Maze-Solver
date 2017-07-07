package nn;

public class Neuron {
    int activation_level = 0;
    int activation_type = 0;
    boolean active = false;
    String name;
    int level = 0;
    int x;
    int y;
    
    Neuron(String n, int al, int at, boolean a, int xh, int yh){
        activation_level = al;
        activation_type = at;
        active = a;
        name = n;
        x = xh;
        y = yh;
    }

    void name() {
        System.out.println(name);
    }
    
    void add_one(int ct, int at){
        level+=(ct*at);
        if(level >= activation_level){
            active = true;
        }
        else{
            active = false;
        }
    }
}
