//trexe to programma
import java.io.*;
import java.util.*;

public class Main{

    //contains the valid moves that we can use on the cube
    // UP DOWN LEFT RIGHT FACE AND BACK, when looking from side 1
    public static String possible_moves[] = {"UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"};

    public static void main(String[] args){

        //making the cube
        Cube initialState = new Cube(true);
        //State initialState = new State(3, false);
        initialState.print();

        SpaceSearcher searcher = new SpaceSearcher();
        Cube terminalState = searcher.AStarClosedSet(initialState); // need K parameter





    }

}
