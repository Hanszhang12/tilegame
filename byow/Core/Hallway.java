package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Hallway {
    private Room main;
    private int width;
    private int height;
    private TETile[][] world;
    private Random r;

    public Hallway(TETile[][] world, Room r, int width, int height, long seed) {
        this.world = world;
        this.r = new Random(seed);
        main = r;
        this.width = width;
        this.height = height;
        createHalls(main);
    }
    public void createHalls(Room start) {
        generateTop(start.topMid());
        generateBottom(start.bottomMid());
        generateLeft(start.leftMid());
        generateRight(start.rightMid());
    }

    public void generateTop(Position top) {
        int x = top.getX();
        int y = top.getY();
        for (int i = 0; i < 5 + r.nextInt(5); i++) {
            while (y < height - 1 && (world[x][y] != Tileset.WALL && world[x][y]
                    != Tileset.FLOOR)) {
                topHelper(x, y);
                y++;
            }
        }
        if (world[x][y] == Tileset.WALL) {
            world[x][y] = Tileset.FLOOR;
        } else {
            world[x][y] = Tileset.WALL;

        }
        world[x - 1][y] = Tileset.WALL;
        world[x + 1][y] = Tileset.WALL;
    }

    private void topHelper(int x, int y) {
        world[x][y - 1] = Tileset.FLOOR;
        world[x][y] = Tileset.FLOOR;
        world[x - 1][y] = Tileset.WALL;
        world[x + 1][y] = Tileset.WALL;
    }


    public void generateBottom(Position bottom) {
        int x = bottom.getX();
        int y = bottom.getY();
        for (int i = 0; i < 5 + r.nextInt(5); i++) {
            while (y > 0 && (world[x][y - 1] != Tileset.WALL && world[x][y - 1] != Tileset.FLOOR)) {
                bottomHelper(x, y);
                y--;
            }
        }
        if (world[x][y] == Tileset.WALL) {
            world[x][y] = Tileset.FLOOR;
        } else {
            world[x][y] = Tileset.WALL;

        }
        world[x - 1][y] = Tileset.WALL;
        world[x + 1][y] = Tileset.WALL;
    }

    private void bottomHelper(int x, int y) {
        world[x][y + 1] = Tileset.FLOOR;
        world[x][y] = Tileset.FLOOR;
        world[x - 1][y] = Tileset.WALL;
        world[x + 1][y] = Tileset.WALL;
    }

    public void generateLeft(Position left) {
        int x = left.getX();
        int y = left.getY();
        for (int i = 0; i < 5 + r.nextInt(5); i++) {
            while (x > 0 && (world[x - 1][y] != Tileset.WALL && world[x - 1][y] != Tileset.FLOOR)) {
                leftHelper(x, y);
                x--;
            }
        }

        if (world[x][y] == Tileset.WALL) {
            world[x][y] = Tileset.FLOOR;
        } else {
            world[x][y] = Tileset.WALL;

        }
        world[x][y - 1] = Tileset.WALL;
        world[x][y + 1] = Tileset.WALL;
    }

    private void leftHelper(int x, int y) {
        world[x + 1][y] = Tileset.FLOOR;
        world[x][y] = Tileset.FLOOR;
        world[x][y + 1] = Tileset.WALL;
        world[x][y - 1] = Tileset.WALL;
    }

    public void generateRight(Position right) {
        int x = right.getX();
        int y = right.getY();

        for (int i = 0; i < 5 + r.nextInt(5); i++) {
            while (x < width - 5 && (world[x][y] != Tileset.WALL && world[x][y] != Tileset.FLOOR)) {
                rightHelper(x, y);
                x++;
            }
        }
        if (world[x][y] == Tileset.WALL) {
            world[x][y] = Tileset.FLOOR;
        } else {
            world[x][y] = Tileset.WALL;

        }
        world[x][y - 1] = Tileset.WALL;
        world[x][y + 1] = Tileset.WALL;
    }

    private void rightHelper(int x, int y) {
        world[x - 1][y] = Tileset.FLOOR;
        world[x][y] = Tileset.FLOOR;
        world[x][y + 1] = Tileset.WALL;
        world[x][y - 1] = Tileset.WALL;
    }
}
