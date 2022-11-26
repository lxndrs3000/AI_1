//trexe to programma
import java.io.*;
import java.util.*;

public class Main{

    //contains the valid moves that we can use on the cube
    // UP DOWN LEFT RIGHT FACE AND BACK, when looking from side 1


    public static void main(String[] args){
        //making the cube
        Cube initialState = new Cube(true);
        System.out.println("\nThe initial State of the cube: ");
        initialState.print();

        SpaceSearcher searcher = new SpaceSearcher();
        Cube terminalState = searcher.AStarClosedSet(initialState); // need K parameter
        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
            // print the path from beggining to start.
            Cube temp = terminalState; // begin from the end.
            ArrayList<Cube> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            for(Cube item: path)
            {
                item.print();
            }
            System.out.println();

        }
        System.out.println("Finish");
    }

}


