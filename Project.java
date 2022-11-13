//trexe to programma
import java.io.*;
import java.util.*;

public class Project{
    //our cube
    public static Cube cube;
    //contains the valid moves that we can use on the cube
    // UP DOWN LEFT RIGHT FACE AND BACK, when looking from side 1
    public static String possible_moves[] = {"UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"};

    public static void main(String[] args){
        
        //making the cube
        cube = new Cube(false);
        cube.print();
    }
}
