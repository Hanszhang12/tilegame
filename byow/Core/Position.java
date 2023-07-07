package byow.Core;

public class Position {
    private int x;
    private int y;

    public Position(int coordX, int coordY) {
        this.x = coordX;
        this.y = coordY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void changeX(int coordX) {
        this.x = coordX;
    }

    public void changeY(int coordY) {
        this.y = coordY;
    }
}
