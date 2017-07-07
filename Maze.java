package nn;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame { 	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numberRows, numberCols;
    private int currentRow,  currentCol;
    private int previousRow, previousCol;

    private JPanel panel;
    private ArrayList<JButton> buttons;

    // Maze constructor
    public Maze(int numRows, int numCols, int curRow, int curCol, String[][] maze, int dirx, int diry) {
    	// Store parameters
        numberRows = numRows;
        numberCols = numCols;
        currentRow = curRow;
        currentCol = curCol;
        previousRow = 0;
        previousCol = 0;
        
        // Create panel and grid
        panel = new JPanel();
        panel.setLayout(new GridLayout(numberRows, numberCols, 0, 0));
        add(panel, BorderLayout.CENTER);
 
        // Build panel of buttons
        buttons = new ArrayList<JButton>(); 
        for (int Row = 0; Row < numberRows; Row++) {
            for (int Col = 0; Col < numberCols; Col++) {

            	// Initialize and add button
            	JButton button = new JButton();
                button.setText(maze[Row][Col]);
                panel.add(button);
                buttons.add(button);
            }
        }
 
        // Configure window
        setLocation(620, 200);
        setSize(new Dimension(numberCols * 50, numberRows * 50));
        setTitle("Maze_Bot");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        redraw(maze, dirx, diry);
    }

    // Move robot
    public void moveTo(int newRow, int newCol, String maze[][], int name, int turn, int dirx, int diry)
    {
    	// Check parameters
    	if ((newRow < 0) || (newRow >= numberRows) || (newCol < 0) || (newCol >= numberCols)) {
    		System.out.println ("Move to " + newRow + "," + newCol + " is out of bounds!");
    	}
    	else {
    		
    		// Store parameters
    		previousRow = currentRow;
    		previousCol = currentCol;
    		currentRow = newRow;
    		currentCol = newCol;
                setTitle("Maze_Bot-Species " + Integer.toString(name) + "-turn " + Integer.toString(turn));

//    		System.out.println ("Bot " + name + " moved to " + newRow + "," + newCol);

    		// Redraw maze
    		redraw(maze, dirx, diry);
    	}
    }
    
    // Redraw maze
    private void redraw(String[][] maze, int dirx, int diry)
    {
        // Compute index and remove icon
        int index = (previousRow * numberCols) + previousCol;
        buttons.get(index).setText(maze[previousRow][previousCol]);
        
        // Compute index and add icon
        index = (currentRow * numberCols) + currentCol;
        if(diry == 0 && dirx == 1){
            buttons.get(index).setText(">");
        }
        else if(diry == 0 && dirx == -1){
            buttons.get(index).setText("<");
        }
        else if(diry == 1 && dirx == 0){
            buttons.get(index).setText("V");
        }
        else if(diry == -1 && dirx == 0){
            buttons.get(index).setText("^");
        }
    }
}