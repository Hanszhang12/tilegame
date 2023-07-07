package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Tests {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);

        // initialize tiles
        TETile[][] env = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                env[x][y] = Tileset.NOTHING;
            }
        }
        Position uL = new Position(20, 20);
        Position lR = new Position(30, 10);


        // draws the world to the screen
        ter.renderFrame(env);
    }



}
