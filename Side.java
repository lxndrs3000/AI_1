import java.io.*;
public class Side{
    //contains the colors of the blocks
    char[][] blocks = new char[3][3];
    //constructor for a side of the cube
    Side(char color){
        fill(color);
    }
    //gives the blocks their color
    void fill(char color){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                blocks[i][j] = color;
            }
        }
    }
    //returns the block of the side
    char[][] get_side(){
        return blocks;
    }
    //sets the blocks of the side
    void set_side(char[][] blocks){
        this.blocks = blocks;
    }

}