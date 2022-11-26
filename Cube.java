import java.util.*;
import java.io.*;
public class Cube implements Comparable<Cube>{
    public static String possible_moves[] = {"UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"};
    //array that holds all 6 of our sides
    public static Side[] sides;
    //array with the 6 colors of the sides
    public static char[] colors;
    public static int[] axon1 = {0,1,2,3};
    public static int[] axon2 = {0,5,2,4};
    public static int[] axon3 = {1,5,3,4};
    private int offPlace;
    private int steps;
    private Cube father = null;
    private long f;

    //constructor for the cube
    Cube(boolean random){
        init(random);
    }

    /*Cube(Cube initial)
    {
        Cube cube_copy = new Cube(false);
        for (int c = 0; c<6; c++){
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    sides[c][i][j] = initial.get_sides()[c].get_side()[i][j];
                }
            }
        }
    }*/
    //cube initialization
    public static void init(boolean random){

        colors = new char[6];
        sides =  new Side[6];
        colors[0] = 'W';
        colors[1] = 'R';
        colors[2] = 'Y';
        colors[3] = 'O';
        colors[4] = 'G';
        colors[5] = 'B';
        for (int c = 0; c<6; c++){
            sides[c] = new Side(colors[c]);
        }

        if(random){

            randomize(20);
        }

    }
    public Side[] get_sides(){
        return sides;
    }

    //scrambles the cube with as many moves as we want
    static void randomize(int times){
        Random rand = new Random();
        for (int i=0;i<times;i++){

            move(possible_moves[rand.nextInt(12)]);

        }
    }



    //intererperets the array of directions
    public static void move(String direction){
        //System.out.println(direction);
        int sider = 0;
        int x = 0;
        int y = 0;
        int[] axon = new int[4];
        boolean follow_x = true;
        boolean clockwise = true;
        boolean clockwiser = true;
        boolean face = true;
        if (direction.startsWith("U")){
            sider = 0;
            x = 3;
            y = 0;
            axon = axon3;
            if (direction.endsWith("R")){
                clockwise = false;
                clockwiser = false;
            }

        }
        if (direction.startsWith("D")){
            sider = 2;
            x = 3;
            y = 2;
            axon = axon3;
            if (direction.endsWith("R")){
                clockwise = false;
                clockwiser = true;
            }else{
                clockwiser = false;
            }

        }
        if (direction.startsWith("R")){
            sider = 5;
            x = 2;
            y = 3;
            follow_x = false;
            axon = axon1;
            if (direction.endsWith("D")){
                clockwise = false;
                clockwiser = false;
            }else{
                clockwise = true;
                clockwiser= true;
            }
        }
        if (direction.startsWith("L")){
            sider = 4;
            x = 0;
            y = 3;
            follow_x = false;
            axon = axon1;
            if (direction.endsWith("D")){
                clockwise = false;
                clockwiser = true;
            }else{
                clockwise = true;
                clockwiser = false;
            }
        }
        if (direction.startsWith("F")){
            sider = 1;
            x = 2;
            y = 3;
            follow_x = false;
            axon = axon2;
            face = true;
            if (direction.endsWith("U")){
                clockwiser = true;
                clockwise = true;
            }else{
                clockwiser = false;
                clockwise = false;
            }

        }
        if (direction.startsWith("B")){
            sider = 3;
            x = 0;
            y = 3;
            follow_x = false;
            axon = axon2;
            face = false;
            if (direction.endsWith("U")){
                clockwise = false;
                clockwiser = true;
            }else{
                clockwiser = false;
            }
        }


        swap_line(x,y,follow_x,axon,clockwise,face);
        rotate(sider,clockwiser);

    }
    //moves 1 line of blocks of the cube
    static void swap_line(int x, int y,boolean follow_x, int[] axon,boolean clockwise,boolean face){
        if (axon!=axon1){
            rotate(3,true);
            rotate(3,true);

        }
        if(axon!=axon2){
            if(clockwise){
                swap_line_clockwise(x, y, follow_x, axon);
            }else{
                swap_line_counter_clockwise(x, y, follow_x, axon);
            }
        }else{
            if(clockwise){
                if (face){
                    swap_axon2_F_clockwise();

                }else{
                    swap_axon2_B_clockwise();
                }

            }else{
                if (face){
                    swap_axon2_F_counter_clockwise();
                }else{
                    swap_axon2_B_counter_clockwise();
                }
            }
        }
        if (axon!=axon1){
            rotate(3,true);
            rotate(3,true);
        }
    }
    static void swap_line_clockwise(int x, int y,boolean follow_x, int[] axon){

        char[][] blocks_first_side_copy = new char[3][3];
        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_first_side_copy[i][j] = sides[axon[0]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon[0]].get_side();
        for(int k=0;k<3;k++){
            if (follow_x){
                for(int i =0;i<x;i++){
                    blocks_copy[i][y] = sides[axon[k+1]].get_side()[i][y];
                }
            }else{
                for(int j =0;j<y;j++){
                    blocks_copy[x][j] = sides[axon[k+1]].get_side()[x][j];
                }
            }
            sides[axon[k]].set_side(blocks_copy);
            blocks_copy=sides[axon[k+1]].get_side();
        }

        if (follow_x){
            for(int i =0;i<x;i++){
                blocks_copy[i][y] = blocks_first_side_copy[i][y];
            }
        }else{
            for(int j =0;j<y;j++){
                blocks_copy[x][j] = blocks_first_side_copy[x][j];
            }
        }
        sides[axon[3]].set_side(blocks_copy);
    }
    static void swap_line_counter_clockwise(int x, int y,boolean follow_x, int[] axon){
        char[][] blocks_last_side_copy = new char[3][3];
        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_last_side_copy[i][j] = sides[axon[3]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon[3]].get_side();
        for(int k=3;k>0;k--){
            if (follow_x){
                for(int i = 0;i<x;i++){
                    blocks_copy[i][y] = sides[axon[k-1]].get_side()[i][y];
                }
            }else{
                for(int j = 0;j<y;j++){
                    blocks_copy[x][j] = sides[axon[k-1]].get_side()[x][j];
                }
            }
            sides[axon[k]].set_side(blocks_copy);
            blocks_copy=sides[axon[k-1]].get_side();

        }
        if (follow_x){
            for(int i =0;i<x;i++){
                blocks_copy[i][y] = blocks_last_side_copy[i][y];
            }
        }else{
            for(int j =0;j<y;j++){
                blocks_copy[x][j] = blocks_last_side_copy[x][j];
            }
        }
        sides[axon[0]].set_side(blocks_copy);
    }


    //rotates the side next to the line of blocks
    static void rotate(int side, boolean clockwise){
        char[][] blocks = new char[3][3];
        char[][] blocks_copy = sides[side].get_side();
        if (clockwise){
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    blocks[2-j][i] = blocks_copy[i][j];
                }
            }
        }else{
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    blocks[j][2-i] = blocks_copy[i][j];
                }
            }
        }

        sides[side].set_side(blocks);

    }

    static void swap_axon2_F_clockwise(){
        char[][] blocks_first_side_copy = new char[3][3];
        Side[] rotated_sides = new Side[4];
        for (int k=0;k<4;k++){
            rotated_sides[k] = new Side('N');
            rotated_sides[k].set_side((sides[axon2[k]].get_side()));

        }

        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_first_side_copy[i][j] = sides[axon2[0]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon2[0]].get_side();
        int l = 2;
        for(int i =0;i<3;i++){
            blocks_copy[i][2] = rotated_sides[3].get_side()[2][l];
            l--;
        }
        sides[axon2[0]].set_side(blocks_copy);

        blocks_copy=sides[axon2[3]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[2][j] = rotated_sides[2].get_side()[j][0];
        }
        sides[axon2[3]].set_side(blocks_copy);
        blocks_copy=sides[axon2[2]].get_side();
        l=2;
        for(int i =0;i<3;i++){
            blocks_copy[l][0] = rotated_sides[1].get_side()[0][i];
            l--;
        }
        sides[axon2[2]].set_side(blocks_copy);
        blocks_copy=sides[axon2[1]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[0][j] = blocks_first_side_copy[j][2];
        }
        sides[axon2[1]].set_side(blocks_copy);
    }

    static void swap_axon2_F_counter_clockwise(){
        char[][] blocks_last_side_copy = new char[3][3];
        Side[] rotated_sides = new Side[4];
        for (int k=0;k<4;k++){
            rotated_sides[k] = new Side('N');
            rotated_sides[k].set_side((sides[axon2[k]].get_side()));
        }

        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_last_side_copy[i][j] = sides[axon2[0]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon2[0]].get_side();
        int l = 2;
        for(int i =0;i<3;i++){
            blocks_copy[i][2] = rotated_sides[1].get_side()[0][i];
        }
        sides[axon2[0]].set_side(blocks_copy);

        blocks_copy=sides[axon2[1]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[0][l] = rotated_sides[2].get_side()[j][0];
            l--;
        }
        sides[axon2[1]].set_side(blocks_copy);

        blocks_copy=sides[axon2[2]].get_side();
        l=2;
        for(int i =0;i<3;i++){
            blocks_copy[i][0] = rotated_sides[3].get_side()[2][i];
        }
        sides[axon2[2]].set_side(blocks_copy);

        blocks_copy=sides[axon2[3]].get_side();
        l=2;
        for(int j =0;j<3;j++){
            blocks_copy[2][j] = blocks_last_side_copy[l][2];
            l--;
        }
        sides[axon2[3]].set_side(blocks_copy);
    }

    static void swap_axon2_B_clockwise(){
        char[][] blocks_first_side_copy = new char[3][3];
        Side[] rotated_sides = new Side[4];
        for (int k=0;k<4;k++){
            rotated_sides[k] = new Side('N');
            rotated_sides[k].set_side((sides[axon2[k]].get_side()));

        }

        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_first_side_copy[i][j] = sides[axon2[0]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon2[0]].get_side();
        int l = 2;
        for(int i =0;i<3;i++){
            blocks_copy[i][0] = rotated_sides[3].get_side()[0][l];
            l--;
        }
        sides[axon2[0]].set_side(blocks_copy);

        blocks_copy=sides[axon2[3]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[0][j] = rotated_sides[2].get_side()[j][2];
        }
        sides[axon2[3]].set_side(blocks_copy);
        blocks_copy=sides[axon2[2]].get_side();
        l=2;
        for(int i =0;i<3;i++){
            blocks_copy[l][2] = rotated_sides[1].get_side()[2][i];
            l--;
        }
        sides[axon2[2]].set_side(blocks_copy);
        blocks_copy=sides[axon2[1]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[2][j] = blocks_first_side_copy[j][0];
        }
        sides[axon2[1]].set_side(blocks_copy);
    }

    static void swap_axon2_B_counter_clockwise(){
        char[][] blocks_last_side_copy = new char[3][3];
        Side[] rotated_sides = new Side[4];
        for (int k=0;k<4;k++){
            rotated_sides[k] = new Side('N');
            rotated_sides[k].set_side((sides[axon2[k]].get_side()));
        }

        for (int i=0;i<3;i++){
            for(int j=0; j<3;j++){
                blocks_last_side_copy[i][j] = sides[axon2[0]].get_side()[i][j];
            }
        }

        char[][] blocks_copy=sides[axon2[0]].get_side();
        int l = 2;
        for(int i =0;i<3;i++){
            blocks_copy[i][0] = rotated_sides[1].get_side()[2][i];
        }
        sides[axon2[0]].set_side(blocks_copy);

        blocks_copy=sides[axon2[1]].get_side();
        for(int j =0;j<3;j++){
            blocks_copy[2][l] = rotated_sides[2].get_side()[j][2];
            l--;
        }
        sides[axon2[1]].set_side(blocks_copy);

        blocks_copy=sides[axon2[2]].get_side();
        for(int i =0;i<3;i++){
            blocks_copy[i][2] = rotated_sides[3].get_side()[0][i];
        }
        sides[axon2[2]].set_side(blocks_copy);

        blocks_copy=sides[axon2[3]].get_side();
        l=2;
        for(int j =0;j<3;j++){
            blocks_copy[0][j] = blocks_last_side_copy[l][0];
            l--;
        }
        sides[axon2[3]].set_side(blocks_copy);
    }
    Cube getFather()
    {
        return this.father;
    }
    void setFather(Cube father)
    {
        this.father = father;
    }

    private int count_all_out_of_place(){
        for(int k=0; k<6;k++){
            this.offPlace+=this.sides[k].count_out_of_place();
        }
        return offPlace/12;
    }
    private int countSteps(){
        while(this.father!=null){
            this.steps++;
        }
        return this.steps;
    }

    public void f(){
        this.f=this.countSteps()+this.count_all_out_of_place();
    }
    public boolean isFinal(){
        return this.count_all_out_of_place()==0;
    }



    //prints the current state of the cube
    static void print(){

        /*/System.out.println("-------------------------------------");

        System.out.print("         "+sides[5].get_side()[0][0]+sides[5].get_side()[0][1]+sides[5].get_side()[0][2]+"\n");
        System.out.print("         "+sides[5].get_side()[1][0]+sides[5].get_side()[1][1]+sides[5].get_side()[1][2]+"\n");
        System.out.print("         "+sides[5].get_side()[2][0]+sides[5].get_side()[2][1]+sides[5].get_side()[2][2]+"\n"+"\n");

        System.out.print(""+sides[0].get_side()[0][0]+sides[0].get_side()[0][1]+sides[0].get_side()[0][2]+"      "+sides[1].get_side()[0][0]+sides[1].get_side()[0][1]+sides[1].get_side()[0][2]+"      "+sides[2].get_side()[0][0]+sides[2].get_side()[0][1]+sides[2].get_side()[0][2]+"      "+sides[3].get_side()[0][0]+sides[3].get_side()[0][1]+sides[3].get_side()[0][2]+"\n");
        System.out.print(""+sides[0].get_side()[1][0]+sides[0].get_side()[1][1]+sides[0].get_side()[1][2]+"      "+sides[1].get_side()[1][0]+sides[1].get_side()[1][1]+sides[1].get_side()[1][2]+"      "+sides[2].get_side()[1][0]+sides[2].get_side()[1][1]+sides[2].get_side()[1][2]+"      "+sides[3].get_side()[1][0]+sides[3].get_side()[1][1]+sides[3].get_side()[1][2]+"\n");
        System.out.print(""+sides[0].get_side()[2][0]+sides[0].get_side()[2][1]+sides[0].get_side()[2][2]+"      "+sides[1].get_side()[2][0]+sides[1].get_side()[2][1]+sides[1].get_side()[2][2]+"      "+sides[2].get_side()[2][0]+sides[2].get_side()[2][1]+sides[2].get_side()[2][2]+"      "+sides[3].get_side()[2][0]+sides[3].get_side()[2][1]+sides[3].get_side()[2][2]+"\n"+"\n");

        System.out.print("         "+sides[4].get_side()[0][0]+sides[4].get_side()[0][1]+sides[4].get_side()[0][2]+"\n");
        System.out.print("         "+sides[4].get_side()[1][0]+sides[4].get_side()[1][1]+sides[4].get_side()[1][2]+"\n");
        System.out.print("         "+sides[4].get_side()[2][0]+sides[4].get_side()[2][1]+sides[4].get_side()[2][2]+"\n");

        System.out.println("\n"+"-------------------------------------");
    }/*/
        System.out.println("-------------------------------------");

        System.out.print("         "+sides[0].get_side()[0][0]+sides[0].get_side()[1][0]+sides[0].get_side()[2][0]+"\n");
        System.out.print("         "+sides[0].get_side()[0][1]+sides[0].get_side()[1][1]+sides[0].get_side()[2][1]+"\n");
        System.out.print("         "+sides[0].get_side()[0][2]+sides[0].get_side()[1][2]+sides[0].get_side()[2][2]+"\n"+"\n");

        System.out.print(""+sides[4].get_side()[0][0]+sides[4].get_side()[1][0]+sides[4].get_side()[2][0]+"      "+sides[1].get_side()[0][0]+sides[1].get_side()[1][0]+sides[1].get_side()[2][0]+"      "+sides[5].get_side()[0][0]+sides[5].get_side()[1][0]+sides[5].get_side()[2][0]+"\n");
        System.out.print(""+sides[4].get_side()[0][1]+sides[4].get_side()[1][1]+sides[4].get_side()[2][1]+"      "+sides[1].get_side()[0][1]+sides[1].get_side()[1][1]+sides[1].get_side()[2][1]+"      "+sides[5].get_side()[0][1]+sides[5].get_side()[1][1]+sides[5].get_side()[2][1]+"\n");
        System.out.print(""+sides[4].get_side()[0][2]+sides[4].get_side()[1][2]+sides[4].get_side()[2][2]+"      "+sides[1].get_side()[0][2]+sides[1].get_side()[1][2]+sides[1].get_side()[2][2]+"      "+sides[5].get_side()[0][2]+sides[5].get_side()[1][2]+sides[5].get_side()[2][2]+"\n"+"\n");

        System.out.print("         "+sides[2].get_side()[0][0]+sides[2].get_side()[1][0]+sides[2].get_side()[2][0]+"\n");
        System.out.print("         "+sides[2].get_side()[0][1]+sides[2].get_side()[1][1]+sides[2].get_side()[2][1]+"\n");
        System.out.print("         "+sides[2].get_side()[0][2]+sides[2].get_side()[1][2]+sides[2].get_side()[2][2]+"\n"+"\n");

        System.out.print("         "+sides[3].get_side()[0][0]+sides[3].get_side()[1][0]+sides[3].get_side()[2][0]+"\n");
        System.out.print("         "+sides[3].get_side()[0][1]+sides[3].get_side()[1][1]+sides[3].get_side()[2][1]+"\n");
        System.out.print("         "+sides[3].get_side()[0][2]+sides[3].get_side()[1][2]+sides[3].get_side()[2][2]+"\n");

        System.out.println("\n"+"-------------------------------------");
    }





    @Override
    public int compareTo(Cube s) {
        return Double.compare(this.offPlace, s.offPlace); // compare based on the heuristic score.
    }

}