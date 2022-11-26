import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class SpaceSearcher {
    private ArrayList<Cube> frontier;
    private HashSet<Cube> closedSet;
    public static String possible_moves[] = {"UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"};
    SpaceSearcher()
    {
        this.frontier = new ArrayList<>();
        this.closedSet = new HashSet<>();
    }


    static Cube copyOf(Cube initial){
        Cube cube_copy = new Cube(false);
        for (int c = 0; c<6; c++){
            char[][] blocks_copy = new char[3][3];
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    blocks_copy[i][j] = initial.get_sides()[c].get_side()[i][j];
                }
            }
            cube_copy.get_sides()[c].set_side(blocks_copy);
        }
        return cube_copy;
    }


    Cube AStarClosedSet(Cube initialState)
    {
        if(initialState.isFinal()) return initialState;
        // step 1: put initial state in the frontier.
        this.frontier.add(initialState);
        // step 2: check for empty frontier.
        while(this.frontier.size() > 0) {
            // step 3: get the first node out of the frontier.
            Cube currentState = this.frontier.remove(0);
            // step 4: if final state, return.
            if(currentState.isFinal()) return currentState;

            // step 5: if the node is not in the closed set, put the children at the frontier.
            // else go to step 2.
            if(!this.closedSet.contains(currentState)) {
                this.closedSet.add(currentState);
                this.frontier.addAll(getChildren(currentState));
                // step 6: sort the frontier based on the heuristic score to get best as first
                Collections.sort(this.frontier); // sort the frontier to get best as first
            }
        }
        return null;
    }
    ArrayList<Cube> getChildren(Cube current){
        ArrayList<Cube> children=new ArrayList<>();

        //Create a new child for every move "UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"
        Cube child=new Cube(false);
        child=copyOf(current);

        child.move("UL");
        child.f();                  //Every child has f(child)=h(child)+g(child)
        child.setFather(current);//Every child has a father
        children.add(child);
        System.out.println("THIS IS :0, With cost of :");
        current.print();
        for(int i=1; i<possible_moves.length;i++){
            child=copyOf(child.getFather());
            child.move(possible_moves[i]);

            System.out.println("THIS IS :"+i+" With cost of :");
            child.print();

            child.f();                      //Every child has f(child)=h(child)+g(child)
            child.setFather(current);          //Every child has a father
            children.add(child);
        }
        return children;
    }

}
