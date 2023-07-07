package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class WorldGenerator {
    private LinkedList<Room> rooms = new LinkedList<>();
    private Random RANDOM;
    private TETile[][] world;
    private long SEED;
    private int width;
    private int height;
    private HashSet<Position> floorTiles = new HashSet<>();

    private TETile wall;
    private TETile floor;
    private TETile coin = Tileset.COIN;


    public WorldGenerator(long seed, int width, int height) {
        this.width = width;
        this.height = height;
        this.world = createWorld();
        this.SEED = seed;
        this.RANDOM = new Random(SEED);
        RandomUtils rdm = new RandomUtils();
        environment();
        createRooms();
        this.rooms = properRooms();
        generateTunnels();
        fillWalls();
        generateCoins();
    }

    private void generateCoins() {
        for (int i = 0; i < 70; i++) {
            Position coinP = new Position(RANDOM.nextInt(width), RANDOM.nextInt(height));
            if (world[coinP.getX()][coinP.getY()] == floor) {
                world[coinP.getX()][coinP.getY()] = coin;
            }
        }
    }

    private void environment() {
        int x = RANDOM.nextInt(5);
        if (x == 0) {
            wall = Tileset.WALL;
            floor = Tileset.FLOOR;
        } else if (x == 1) {
            wall = Tileset.MOUNTAIN;
            floor = Tileset.SAND;
        } else if (x == 2) {
            wall = Tileset.TREE;
            floor = Tileset.WATER;
        } else if (x == 3) {
            wall = Tileset.LOCKED_DOOR;
            floor = Tileset.GRASS;
        } else if (x == 4) {
            wall = Tileset.FENCE;
            floor = Tileset.LAVA;
        }
    }

    public void createRooms() {
        for (int i = 0; i < 100000; i++) {
            Position x = new Position(RANDOM.nextInt(width + 2), RANDOM.nextInt(height - 4));
            Position y = new Position(x.getX() + 5 + RANDOM.nextInt(10), x.getY() - 5
                    - RANDOM.nextInt(10));
            Room temp = new Room(world, x, y, floor);
            boolean collision = true;
            if (!rooms.isEmpty()) {
                for (Room r : rooms) {
                    collision = collide(r, temp);
                    if (!collision) {
                        break;
                    }
                }
                if (collision) {
                    addRoom(temp);
                    rooms.add(temp);
                }
            } else {
                addRoom(temp);
                rooms.add(temp);
            }
        }
    }

    //List of all rooms actually used from original list
    public LinkedList<Room> properRooms() {
        LinkedList<Room> proper = new LinkedList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Room curr = rooms.get(i);
            if (curr.getUL().getX() > curr.getLR().getX()
                    || curr.getUL().getY() < curr.getLR().getY()) {
                continue;
            }
            if (curr.getUL().getX() <= 0 || curr.getUL().getX() >= width
                    || curr.getLR().getX() >= width) {
                continue;
            }
            if (curr.getUL().getY() <= 0 || curr.getUL().getY() >= height
                    || curr.getLR().getY() < 0) {
                continue;
            }
            proper.add(curr);
        }
        return proper;
    }

    public TETile[][] createWorld() {
        world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    public void addRoom(Room r) {
        r.createRoom(world, width, height, floorTiles);
    }

    public boolean collide(Room r1, Room r2) {
        return r1.checkCollision(world, r2) && r2.checkCollision(world, r1);
    }

    public TETile[][] world() {
        return world;
    }

    public void generateTunnels() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < rooms.size(); j++) {
                Room curr = rooms.get(j);

                Position top = curr.topMid();
                Tunnel pathTop = new Tunnel(world, top, RANDOM, width, height,
                        floorTiles, 2, floor);

                Position bottom = curr.bottomMid();
                Tunnel pathBottom = new Tunnel(world, bottom, RANDOM, width, height,
                        floorTiles, 3, floor);

                Position right = curr.rightMid();
                Tunnel pathRight = new Tunnel(world, right, RANDOM, width, height,
                        floorTiles, 0, floor);

                Position left = curr.leftMid();
                Tunnel pathLeft = new Tunnel(world, left, RANDOM, width, height,
                        floorTiles, 1, floor);

            }
        }

    }
    private void fillWalls() {
        Iterator iter = floorTiles.iterator();
        while (iter.hasNext()) {
            checkFloor((Position) iter.next());
        }
    }
    public void checkFloor(Position x) {
        int lRx = x.getX() - 1;
        int lRy = x.getY() - 1;
        int uRx = x.getX() + 1;
        int uRy = x.getY() + 1;
        if (lRx < 0 || lRy < 0 || uRx > width || uRy > height) {
            return;
        }
        for (int i = lRx; i <= uRx; i++) {
            for (int j = lRy; j <= uRy; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = wall;
                }
            }
        }
    }

    public TETile floor() {
        return floor;
    }

    public TETile wall() {
        return wall;
    }


}
