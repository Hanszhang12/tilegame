package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Random;

public class Avatar {
    private TETile body;
    private Position location;
    private TETile[][] world;
    private int WIDTH;
    private int HEIGHT;
    private Random r;
    private boolean check;
    private TETile f;
    private TETile w;
    private int score = 0;
    private boolean prevChar = false;
    TERenderer ter = new TERenderer();

    public Avatar(TETile[][] t, Random gen, int width, int height, boolean x, TETile floor,
                  TETile wall) {
        check = x;
        if (check) {
            body = Tileset.AVATAR;
        } else {
            body = Tileset.AVATAR2;
        }
        this.world = t;
        this.f = floor;
        this.w = wall;
        r = gen;
        WIDTH = width;
        HEIGHT = height;
        placeAvatar();
    }


    public void placeAvatar() {
        while (true) {
            int x = r.nextInt(WIDTH);
            int y = r.nextInt(HEIGHT);
            Position p = new Position(x, y);
            if (world[x][y] == f) {
                world[x][y] = body;
                location = p;
                return;
            }
        }
    }

    public String interact(Avatar other, Engine engine) {
        if (StdDraw.hasNextKeyTyped()) {
            char p = StdDraw.nextKeyTyped();
            if (p == 'w') {
                voidSave();
                moveUp();
                return "w";
            } else if (p == 's') {
                voidSave();
                moveDown();
                return "s";
            } else if (p == 'a') {
                voidSave();
                moveLeft();
                return "a";
            } else if (p == 'd') {
                voidSave();
                moveRight();
                return "d";
            }
            if (p == 'i') {
                voidSave();
                other.moveUp();
                return "i";
            } else if (p == 'k') {
                voidSave();
                other.moveDown();
                return "k";
            } else if (p == 'j') {
                voidSave();
                other.moveLeft();
                return "j";
            } else if (p == 'l') {
                voidSave();
                other.moveRight();
                return "l";
            }
            if (p == 't') {
                voidSave();
                engine.newWorld();
                return "t";
            }

            if (p == ':') {
                if (prevChar) {
                    prevChar = false;
                } else {
                    prevChar = true;
                }
            }
            if (p == 'q' && prevChar) {
                engine.saveGame();

            }

        }
        return "";
    }

    public void voidSave() {
        prevChar = false;
    }

    public void moveUp() {
        int x = location.getX();
        int y = location.getY();
        if (world[x][y + 1] == w || world[x][y + 1] == Tileset.AVATAR
                || world[x][y + 1] == Tileset.AVATAR2) {
            return;
        } else if (world[x][y + 1] == f) {
            world[x][y + 1] = body;
            world[x][y] = f;
            location = new Position(x, y + 1);
            //ter.renderFrame(world);
        } else if (world[x][y + 1] == Tileset.COIN) {
            world[x][y + 1] = body;
            world[x][y] = f;
            score++;
            location = new Position(x, y + 1);
            //ter.renderFrame(world);
        }
    }

    public void moveDown() {
        int x = location.getX();
        int y = location.getY();
        if (world[x][y - 1] == w || world[x][y - 1] == Tileset.AVATAR
                || world[x][y - 1] == Tileset.AVATAR2) {
            return;
        } else if (world[x][y - 1] == f) {
            world[x][y - 1] = body;
            world[x][y] = f;
            location = new Position(x, y - 1);
            //ter.renderFrame(world);
        } else if (world[x][y - 1] == Tileset.COIN) {
            world[x][y - 1] = body;
            world[x][y] = f;
            score++;
            location = new Position(x, y - 1);
            //ter.renderFrame(world);
        }
    }

    public void moveRight() {
        int x = location.getX();
        int y = location.getY();
        if (world[x + 1][y] == w || world[x + 1][y] == Tileset.AVATAR
                || world[x + 1][y] == Tileset.AVATAR2) {
            return;
        } else if (world[x + 1][y] == f) {
            world[x + 1][y] = body;
            world[x][y] = f;
            location = new Position(x + 1, y);
            //ter.renderFrame(world);
        } else if (world[x + 1][y] == Tileset.COIN) {
            world[x + 1][y] = body;
            world[x][y] = f;
            score++;
            location = new Position(x + 1, y);
            //ter.renderFrame(world);
        }
    }

    public void moveLeft() {
        int x = location.getX();
        int y = location.getY();
        if (world[x - 1][y] == w || world[x - 1][y] == Tileset.AVATAR
                || world[x - 1][y] == Tileset.AVATAR2) {
            return;
        } else if (world[x - 1][y] == f) {
            world[x - 1][y] = body;
            world[x][y] = f;
            location = new Position(x - 1, y);
            //ter.renderFrame(world);
        } else if (world[x - 1][y] == Tileset.COIN) {
            world[x - 1][y] = body;
            world[x][y] = f;
            score++;
            location = new Position(x - 1, y);
            //ter.renderFrame(world);
        }
    }

    public Position getPos() {
        return location;
    }

    public int getScore() {
        return score;
    }


}
