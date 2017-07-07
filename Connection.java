package nn;

public class Connection {
    int connection_type = 0;
    String name;
    int in;
    int out;
    boolean done = false;
    
    Connection(String n, int ct, int i, int o){
        connection_type = ct;
        name = n;
        in = i;
        out = o;
    }
    void name() {
        System.out.println(name);
    }
    
    void reset() {
        done = false;
    }
}
