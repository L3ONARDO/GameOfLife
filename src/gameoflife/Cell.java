/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author s143969
 */
public class Cell extends JLabel implements MouseListener {

    
    public boolean alive;  // "true" when the cell is alive and false when the cell is dead
    public boolean nextgen;  // The "alive" value for the next generation
    private int numNeighbours;
    public final Color ALIVE = new Color(0x000000);// black
    public final Color DEAD = new Color(0xffffff);// white
    //This part is for extensions
    public static boolean pressed = false; //Mouse event: pressed
    public static boolean currentCell = false; //record current cell state
    
    public Cell(boolean state, int n){
        alive = state;
        numNeighbours = n;
        this.setSize(new Dimension(10, 10));
        this.setOpaque(true);
    }
    
    /*This Changes the alive/dead state of the cell*/
    public void setAlive(boolean state){
        this.alive = state;
    }
    
    /*This returns the alive/dead state of the cell*/
    public boolean isAlive(){
        return this.alive;
    }
    
    /*This sets number of the cell to n*/
    public void setNumNeighbours(int n){
        this.numNeighbours = n;
    }
    
    public boolean getNextGen(){
        return nextgen;
    }
    
    /*takes the cell to the next generation: it changes the instance 
    variable alive according to the rules above, using the value of 
    the instance variables numNeighbors and alive.*/
    public void update(){          
        if(alive == true){
            nextgen = !(numNeighbours < 2 || numNeighbours > 3);
        }
        
        else{
            nextgen = numNeighbours == 3;
        }
        colorRearrange();
    }
    
    /*change the color if cell is dead/alive*/
    public void colorRearrange(){
        if(alive == true){
            setBackground(ALIVE);
        }
        else{
            setBackground(DEAD);
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //Do nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setAlive(!isAlive());//Inverse a cell once click
        currentCell = isAlive();
        pressed = true;
        colorRearrange();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }

}
