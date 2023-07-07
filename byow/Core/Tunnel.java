package byow.Core;

import byow.TileEngine.TETile;

import java.util.HashSet;
import java.util.Random;

public class Tunnel {
    private TETile[][] world;
    private Position start;
    private int WIDTH;
    private int HEIGHT;
    private Random rand;
    private HashSet<Position> floorTiles;
    private TETile f;

    public Tunnel(TETile[][] world, Position start, Random r, int width, int height,
                  HashSet<Position> tiles, int whichWay, TETile floor) {
        this.f = floor;
        this.world = world;
        this.start = start;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.rand = r;
        this.floorTiles = tiles;
        //0 means right, 1 means left, 2 means up, 3 means down
        if (whichWay == 0) {
            horizontalTunnel(WIDTH - 1, floorTiles, true);
        } else if (whichWay == 1) {
            horizontalTunnel(WIDTH - 1, floorTiles, false);
        } else if (whichWay == 2) {
            verticalTunnel(HEIGHT - 1, floorTiles, true);
        } else if (whichWay == 3) {
            verticalTunnel(HEIGHT - 1, floorTiles, false);
        }
    }

    public void horizontalTunnel(int length, HashSet<Position> tiles, boolean direction) {
        //direction being true indicates right, false means left
        int y = start.getY();
        int x = start.getX();
        int end = 0;
        boolean pathway = false;
        if (direction) {
            x = x + 1;
            for (int i = x; i <= length; i++) {
                if (world[i][y].equals(f)) {
                    pathway = true;
                    end = i;
                    break;
                }
            }
            if (pathway) {
                for (int j = x; j <= end; j++) {
                    /*
                    Unused condition:

                    if (y + 1 <= getBoundaryVertical()) {
                        world[j][y + 1] = Tileset.WALL;
                    }
                    if (y - 1 >= 0) {
                        world[j][y - 1] = Tileset.WALL;
                    }
                    */
                    world[j][y] = f;
                    Position p = new Position(j, y);
                    tiles.add(p);
                }
            }
        } else {
            x = x + 1;
            for (int i = x; i >= 0; i--) {
                if (world[i][y].equals(f) & i != x) {
                    pathway = true;
                    end = i;
                    break;
                }
            }
            if (pathway) {
                for (int j = x; j >= end; j--) {
                    /*
                    Unused condition:

                    if (y + 1 <= getBoundaryVertical()) {
                        world[j][y + 1] = Tileset.WALL;
                    }
                    if (y - 1 >= 0) {
                        world[j][y - 1] = Tileset.WALL;
                    }
                    */
                    world[j][y] = f;
                    Position p = new Position(j, y);
                    tiles.add(p);
                }
            }
        }


    }

    public void verticalTunnel(int length, HashSet<Position> tiles, boolean orientation) {
        //orientation as true means up, as false means down
        int x = start.getX();
        int y = start.getY();
        int end = 0;
        boolean pathway = false;
        if (orientation) {
            y = y - 1;
            for (int i = y; i <= length; i++) {
                if (world[x][i].equals(f) && i != y) {
                    pathway = true;
                    end = i;
                    break;
                }
            }
            if (pathway) {
                for (int j = y; j <= end; j++) {
                    /*
                    Unused condition:

                    if (x + 1 < getBoundaryHorizontal()) {
                        world[x + 1][j] = Tileset.WALL;
                    }
                    if (x - 1 >= 0) {
                        world[x - 1][j] = Tileset.WALL;
                    }
                    */
                    world[x][j] = f;
                    Position p = new Position(x, j);
                    tiles.add(p);
                }
            }
        } else {
            y = y + 1;
            for (int i = y; i >= 0; i--) {
                if (world[x][i].equals(f) && i != y) {
                    pathway = true;
                    end = i;
                    break;
                }
            }
            if (pathway) {
                for (int j = y; j >= end; j--) {
                    /*
                    Unused condition:

                    if (x + 1 < getBoundaryHorizontal()) {
                        world[x + 1][j] = Tileset.WALL;
                    }
                    if (x - 1 >= 0) {
                        world[x - 1][j] = Tileset.WALL;
                    }
                    */
                    world[x][j] = f;
                    Position p = new Position(x, j);
                    tiles.add(p);
                }
            }
        }
    }

    public int getBoundaryHorizontal() {
        return WIDTH;
    }

    public int getBoundaryVertical() {
        return HEIGHT;
    }
}
