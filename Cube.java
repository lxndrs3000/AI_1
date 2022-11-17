import java.util.*;
import java.io.*;  


public class Cube{
    public static String possible_moves[] = {"UL","UR","DL","DR","RU","RD","LU","LD","FU","FD","BU","BD"};
    //array that holds all 6 of our sides
    public static Side[] sides;
    //array with the 6 colors of the sides
    public static char[] colors;
//constructor for the cube
    Cube(boolean random){
    init(random);
}
    //cube initialization
    public static void init(boolean random){

        /*
          sideG                   □
    sideW sideR sideY sideO     □ □ □ □
          sideB                   □
     */
        
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
            
            randomize(10);
        }
            
    }
//scrambles the cube with as many moves as we want
    static void randomize(int times){
        Random rand = new Random();
        for (int i=0;i<times;i++){
            //move(possible_moves[rand.nextInt(11)]);
        }
    }

    //intererperets the array of directions
     /*/public static void move(String direction){
        if (direction.startsWith("U")){
            if (direction.endsWith("L")){//UP LEFT
                swap_line(get_LEFT(4));
                rotate(1,-1);
            }
            if (direction.endsWith("R")){//UP RIGHT
                swap_line(get_RIGHT(4));
                rotate(1,1);
            }
        }
        if (direction.startsWith("D")){
            for (int i=0; i<4; i++){
             
                swap_line(get_DOWN(1));
            }
        }
        if (direction.startsWith("R")){
            for (int i=0; i<4; i++){    
             
                swap_line(get_RIGHT(1));
            }
        }
        if (direction.startsWith("L")){
            for (int i=0; i<4; i++){
             
                swap_line(get_LEFT(1));
            }
        }
        if (direction.startsWith("F")){
            for (int i=0; i<4; i++){
             
                swap_line(get_LEFT(1));
            }
        }
        if (direction.startsWith("B")){
            for (int i=0; i<4; i++){
             
                swap_line(get_LEFT(1));
            }
        }
      
    }/*/
    //getters for which sides are next to each other
    static int get_UP(int side){
        int up = 1;
        if (side == 1 || side == 2 || side == 3 || side == 4){
            up = (side+1)%4;
        }
        return up;
    }
    static int get_DOWN(int side){
        int down = 4;
        if (side == 1 || side == 2 || side == 3 || side == 4){
            down = (side-1)%4;
        }
        return down;
    }
    static int get_LEFT(int side){
        int left = 0;
        if (side == 1 || side == 2 || side == 3 || side == 4) { left = 5; }
        if (side == 5) { left = 3;}
        if (side == 6) {left = 1;}
        return left;

    }
    static int get_RIGHT(int side){
        int right = 6;
        if (side == 1 || side == 2 || side == 3 || side == 4){
            right = 6;
        }
        if (side == 5) {right = 1;}
        if (side == 6) {right = 3;}
        return right;
    }
    //moves 1 line of blocks of the cube
    /*/static void swap_line(int side, int turn,int x, int y){
        char[][] blocks_copy = sides[side].get_side();
        int next_side = 0;
        if (turn == 1){
            for(int k = 0; k<4;k++){
                next_side = get_RIGHT(side);
                for(int i =0;i<3;i++){
                    
                }
                sides[next_side].set_side(blocks_copy);
            }
        }else{

        }
    }/*/

//rotates the side next to the line of blocks
    static void rotate(int side,int turn){  
        char[][] blocks = new char[3][3];
        char[][] blocks_copy = sides[side].get_side();
        if (turn == 1){
            int y =2;
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    blocks[i][j] = blocks_copy[y][i];
                    y = (y-1)%3;
                }
            }
        }else{
            int x = 2;
            int y = 2;
            for (int i = 0; i<3; i++){
                for (int j = 0; j<3; j++){
                    blocks[x][j] = blocks_copy[y][i];
                    y = (y-1)%3;
                }
                x = (x-1)%3;
            }
        }
        
        sides[side].set_side(blocks);
    }
//prints the current state of the cube
    void print(){

        System.out.println("-------------------------------------");

        System.out.print("         "+sides[4].get_side()[0][0]+sides[4].get_side()[0][1]+sides[4].get_side()[0][2]+"\n");
        System.out.print("         "+sides[4].get_side()[1][0]+sides[4].get_side()[1][1]+sides[4].get_side()[1][2]+"\n");
        System.out.print("         "+sides[4].get_side()[2][0]+sides[4].get_side()[2][1]+sides[4].get_side()[2][2]+"\n"+"\n");

        System.out.print(""+sides[0].get_side()[0][0]+sides[0].get_side()[0][1]+sides[0].get_side()[0][2]+"      "+sides[1].get_side()[0][0]+sides[1].get_side()[0][1]+sides[1].get_side()[0][2]+"      "+sides[2].get_side()[0][0]+sides[2].get_side()[0][1]+sides[2].get_side()[0][2]+"      "+sides[3].get_side()[0][0]+sides[3].get_side()[0][1]+sides[3].get_side()[0][2]+"\n");
        System.out.print(""+sides[0].get_side()[1][0]+sides[0].get_side()[1][1]+sides[0].get_side()[1][2]+"      "+sides[1].get_side()[1][0]+sides[1].get_side()[1][1]+sides[1].get_side()[1][2]+"      "+sides[2].get_side()[1][0]+sides[2].get_side()[1][1]+sides[2].get_side()[1][2]+"      "+sides[3].get_side()[1][0]+sides[3].get_side()[1][1]+sides[3].get_side()[1][2]+"\n");
        System.out.print(""+sides[0].get_side()[2][0]+sides[0].get_side()[2][1]+sides[0].get_side()[2][2]+"      "+sides[1].get_side()[2][0]+sides[1].get_side()[2][1]+sides[1].get_side()[2][2]+"      "+sides[2].get_side()[2][0]+sides[2].get_side()[2][1]+sides[2].get_side()[2][2]+"      "+sides[3].get_side()[2][0]+sides[3].get_side()[2][1]+sides[3].get_side()[2][2]+"\n"+"\n");

        System.out.print("         "+sides[5].get_side()[0][0]+sides[5].get_side()[0][1]+sides[5].get_side()[0][2]+"\n");
        System.out.print("         "+sides[5].get_side()[1][0]+sides[5].get_side()[1][1]+sides[5].get_side()[1][2]+"\n");
        System.out.print("         "+sides[5].get_side()[2][0]+sides[5].get_side()[2][1]+sides[5].get_side()[2][2]+"\n");

        System.out.println("\n"+"-------------------------------------");
       
        /*for (int k = 0; k < 6; k++){
            for(int i = 0; i<3; i++){
                for(int j = 0; j<3; j++){
                    System.out.print(sides[k].get_side()[i][j]);
                }
                System.out.println("");
            }
            System.out.println("");
        }*/
    }
}