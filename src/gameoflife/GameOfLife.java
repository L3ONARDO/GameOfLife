/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author s143969
 */
public class GameOfLife extends JFrame {

    Cell[][] grid;
    String birthFilename;
    int row;
    int col;
    
    
    public void draw(){
        JFrame frame = new JFrame("Game_of_Life");//GUI
        JButton startButton = new JButton(("Start"));//Start button
        GridLayout gridLayout = new GridLayout(row, col, 2, 2);
        JPanel gridPanel = new JPanel();

        gridPanel.setLayout(gridLayout);

        // Add all cells in the grid to the panel
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Cell cell = grid[r][c];
                gridPanel.add(cell);
            }
        }
        
        //Create a start button
        startButton.addActionListener((ActionEvent e) -> {
            //blah
        });

        frame.add(gridPanel);//Add grid
        frame.add(startButton, BorderLayout.SOUTH);//put the button down
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /*Calculates the number of LIVING neighbours*/
    public int calcNumNeighbours(int i, int j) {                    
        int counter = 0;

        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {
                try{
                    if (grid[i + n][j + m] != null && grid[i + n][j + m].isAlive() == true) {
                        if ((i + n == i && j + m == j) == false) {
                            counter++;
                        }
                    }
                }
                catch(ArrayIndexOutOfBoundsException e){}
            }
        }

        return counter;// And returns it
    }

    /*uses calcNumNeighbours for every single cell*/
    public void setAllNeighbours() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                try{
                    grid[j][i].setNumNeighbours(calcNumNeighbours(j, i));
                }
                catch(ArrayIndexOutOfBoundsException e){}
            }
        }
    }
   
    /*Creates the grid and reads the inital generation from input*/
    public void readInitial() throws IOException {
        //In case we have an empty, catch the error
        try (BufferedReader br = new BufferedReader(new FileReader("birth.txt"))) {
            StringBuilder sb = new StringBuilder();//construct string 
            String line = br.readLine();//Read line by line
            List<String> lines = new ArrayList<>();//this store lines
            //We read the file if not empty, also store lines
            while (line != null) {
                lines.add(line);
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            birthFilename = sb.toString();//Store initial generation
            String[] data = lines.toArray(new String[lines.size()]);//Store line list 
            // Now start get the rows and cols
            String sizes = data[0];//first line has the row and col
            //Then we remove the first line
            String[] initGrid = Arrays.copyOfRange(data, 1, data.length);
            //Store the row and col as int
            row = Integer.parseInt(Arrays.asList(sizes.trim().split(" ")).get(0));
            col = Integer.parseInt(Arrays.asList(sizes.trim().split(" ")).get(1));
            //initial size of the grid
            grid = new Cell[row][col];
            //First we make a string[][] to hold the initGrid
            char [][] holder = new char[row][col];
            
            for (int i = 0; i<row; i++) {
                int c1 = 0;
                int c2 = 0;
                while (c2 < col) {
                    char temp = initGrid[i].charAt(c1);
                    if (temp == '*') {
                        holder[i][c2] = temp;
                        grid[i][c2] = new Cell(true, 0);
                        grid[i][c2].colorRearrange();
                        c2++;
                    } else if (temp == '.') {
                        holder[i][c2] = temp;
                        grid[i][c2] = new Cell(false, 0);
                        grid[i][c2].colorRearrange();
                        c2++;
                    }
                    c1++;
                }
            }
        }
        setAllNeighbours();
        draw();
    }
    
    public void nextGeneration(){                           
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                grid[j][i].update();// Updates the attribute "nextgen" of cell
            }
        }
        
        for(int i = 0; i < row; i++){// Assigns "nextgen"'s value to "alive"
            for(int j = 0; j < col; j++){
                grid[j][i].setAlive(grid[j][i].getNextGen());
            }
        }
        
        setAllNeighbours();
    }
    
    public void play() throws IOException{
        readInitial();
        draw();
    }
    
    public static void main(String[] args) throws IOException {
        (new GameOfLife()).play();
    } 
}