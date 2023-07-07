package byow.Core;

import byow.TileEngine.TETile;

import java.util.HashSet;

public class Room {
    private Position UL;
    private Position LR;
    private TETile[][] world;
    private TETile floor;
    private TETile wall;


    public Room(TETile[][] world, Position upperLeft, Position lowerRight, TETile f) {
        this.floor = f;
        this.UL = upperLeft;
        this.LR = lowerRight;
        this.world = world;
    }

    public void createRoom(TETile[][] place, int width, int height, HashSet<Position> tiles) {
        if (UL.getX() > LR.getX() || UL.getY() < LR.getY()) {
            return;
        }
        if (UL.getX() <= 0 || UL.getX() >= width || LR.getX() >= width) {
            return;
        }
        if (UL.getY() <= 0 || UL.getY() >= height || LR.getY() < 0) {
            return;
        }
        //createWalls(place);
        fill(place, tiles);
    }
    /*
    The method we stopped using:
    public void createWalls(TETile[][] place) {
        for (int i = UL.getX(); i < LR.getX(); i++) {
            for (int j = LR.getY(); j < UL.getY(); j++) {
                place[i][j] = Tileset.WALL;
            }
        }
    }
    */
    public void fill(TETile[][] place, HashSet<Position> tiles) {
        for (int i = UL.getX() + 1; i <= LR.getX() - 1; i++) {
            for (int j = LR.getY() + 1; j <= UL.getY() - 1; j++) {
                place[i][j] = floor;
                Position p = new Position(i, j);
                tiles.add(p);
            }
        }
    }
    //false means they collide, true means they don't
    public boolean checkCollision(TETile[][] place, Room other) {
        int otherLRx = other.getLR().getX();
        int otherLRy = other.getLR().getY();
        int otherULx = other.getUL().getX();
        int otherULy = other.getUL().getY();
        int otherURx = other.getUR().getX();
        int otherURy = other.getUR().getY();
        int otherLLx = other.getLL().getX();
        int otherLLy = other.getLL().getY();

        if (otherLRx >= getUL().getX() && otherLRx <= getLR().getX()
                && otherLRy <= getUL().getY() && otherLRy >= getLR().getY()) {
            return false;
        }
        if (otherULx <= getLR().getX() && otherULx >= getUL().getX()
                && otherULy >= getLR().getY() && otherULy <= getUL().getY()) {
            return false;
        }
        if (otherURx >= getLL().getX() && otherURx <= getLR().getX()
                && otherURy >= getLL().getY() && otherURy <= getUL().getY()) {
            return false;
        }
        if (otherLLx <= getUR().getX() && otherLLx >= getUL().getX()
                && otherLLy <= getUR().getY() && otherLLy >= getLR().getY()) {
            return false;
        }
        if (otherULx >= getUL().getX() && otherURx <= getUR().getX()
                && otherULy >= getUL().getY() && otherLLy <= getLR().getY()) {
            return false;
        }
        if (otherULx <= getUL().getX() && otherURx >= getUR().getX()
                && otherULy <= getUL().getY() && otherLLy >= getLR().getY()) {
            return false;
        }
        if (otherULx >= getUL().getX() && otherURx <= getUR().getX()
                && otherULy <= getUL().getY() && otherLLy >= getLR().getY()) {
            return false;
        }

        return true;
    }

    public Position getUL() {
        return UL;
    }

    public Position getUR() {
        return new Position(LR.getX(), UL.getY());
    }

    public Position getLR() {
        return LR;
    }

    public Position getLL() {
        return new Position(UL.getX(), LR.getY());
    }

    public Position topMid() {
        int middle = (UL.getX() + LR.getX()) / 2;
        return new Position(middle, UL.getY());
    }

    public Position bottomMid() {
        int middle = (UL.getX() + LR.getX()) / 2;
        return new Position(middle, LR.getY());
    }

    public Position leftMid() {
        int middle = (UL.getY() + LR.getY()) / 2;
        return new Position(UL.getX(), middle);
    }

    public Position rightMid() {
        int middle = (UL.getY() + LR.getY()) / 2;
        return new Position(LR.getX() - 1, middle);
    }
}
