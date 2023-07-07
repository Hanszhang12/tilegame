package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private StringBuilder SEED = new StringBuilder();
    private static String savedString;
    private TETile floor;
    private TETile wall;
    private TETile[][] w;
    private String cd;
    private Avatar p1;
    private Avatar p2;
    private boolean prev = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        setCanvas();
        mainMenu();
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char p = StdDraw.nextKeyTyped();
                if (p == 'n') {
                    seedMenu(SEED);
                    getSeed();
                } else if (p == 'q') {
                    savedString = "";
                    System.exit(0);
                } else if (p == 'l') {
                    savedString = fileReader();
                    load();
                }
            }
        }
    }

    public void getSeed() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char p = StdDraw.nextKeyTyped();
                if (p == 's') {
                    init();
                    while (true) {
                        play(cd, p1, p2);
                        slow(50);
                    }
                } else {
                    SEED.append(p);
                }
                seedMenu(SEED);
            }
        }
    }

    public void newWorld() {
        long l = Long.valueOf(SEED.toString());
        Random r = new Random(l);
        SEED = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            SEED.append(r.nextInt(10));
        }
        init();
        while (true) {
            play(cd, p1, p2);
            slow(50);
        }


    }

    private void init() {
        cd = SEED.toString();
        cd = 'n' + cd + 's';
        w = interactWithInputString(cd);
        Random r = new Random(100);
        p1 = new Avatar(w, r, WIDTH, HEIGHT, true, floor, wall);
        p2 = new Avatar(w, r, WIDTH, HEIGHT, false, floor, wall);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 15));
        StdDraw.enableDoubleBuffering();
    }

    private void play(String s, Avatar a, Avatar b) {
        ter.renderFrame(w);
        avatarCoord(b, a);
        displayTime();
        hUD(w);
        savedString = savedString + a.interact(b, this);
        StdDraw.show();
    }

    public void slow(int mili) {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void displayTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        String text = formatter.format(date);
        StdDraw.text(70, 29, text);
        //StdDraw.show();

    }

    public void saveGame() {
        File f = new File("byow/Core/SavedFile.txt");
        try {
            System.out.println(savedString);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter writer = new FileWriter(f);
            writer.write(savedString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public String fileReader() {
        StringBuilder s = new StringBuilder();
        try {
            FileReader fr = new FileReader("byow/Core/SavedFile.txt");
            int i;
            while ((i = fr.read()) != -1) {
                s.append((char) i);

            }

            System.out.println(s.toString());
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
        return s.toString();
    }


    private void load() {
        w = interactWithInputString(savedString);
        while (true) {
            ter.renderFrame(w);
            play(savedString, p1, p2);
            slow(50);

        }
    }

    private void hUD(TETile[][] world) {
        String text = " ";
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x >= WIDTH || y >= HEIGHT || x <= 0 || y <= 0) {
            return;
        }
        if (world[x][y] == floor) {
            text = "Floor";
        } else if (world[x][y] == wall) {
            text = "Wall";
        } else if (world[x][y] == Tileset.NOTHING) {
            text = " ";
        } else if (world[x][y] == Tileset.AVATAR) {
            text = "Player 1";
        } else if (world[x][y] == Tileset.AVATAR2) {
            text = "Player 2";
        } else if (world[x][y] == Tileset.COIN) {
            text = "Coin";
        }
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2, 29, text);
        resimulate();
        //StdDraw.show();
    }

    private void resimulate() {
        StdDraw.text((WIDTH / 2) + 10, 29, "NEW GAME(t)");
    }
    private void avatarCoord(Avatar a, Avatar b) {
        Position pos1 = p1.getPos();
        Position pos2 = p2.getPos();
        StdDraw.setPenColor(Color.white);
        StdDraw.text(6, 29, "Player 1 X: " + pos1.getX() + " Y: " + pos1.getY());
        StdDraw.text(20, 29, "Player 2 X: " + pos2.getX() + " Y: " + pos2.getY());
        StdDraw.text(3, 28, "Score: " + p1.getScore());
        StdDraw.text(17, 28, "Score: " + p2.getScore());

        //StdDraw.text(40, 29, savedString);
        //StdDraw.show();
    }

    private void seedMenu(StringBuilder s) {
        String st = s.toString();
        StdDraw.clear(Color.WHITE);
        StdDraw.text(WIDTH / 2, HEIGHT - 10, "PRESS S WHEN DONE");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "ENTER A SEED: " + st);
        StdDraw.show();
    }

    public void setCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.clear(Color.WHITE);
        StdDraw.enableDoubleBuffering();
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "CS61B: THE GAME");
    }

    public void mainMenu() {
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 3, "New Game (N)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 6, "Load Game (L)");
        StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 9, "Quit (Q)");

    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        char first = input.charAt(0);
        if (first == 'l') {
            savedString = fileReader();
        } else {
            savedString = input;
        }
        boolean x;
        try {
            int d = Integer.parseInt(input.substring(input.length() - 2, input.length() - 1));
            x = true;
        } catch (NumberFormatException | NullPointerException nfe) {
            x = false;
        }
        if (!x) {
            w = runThrough(input);
            return w;
        }
        String sub = input.substring(1, input.length() - 1);
        long seed = Long.valueOf(sub);
        WorldGenerator yeet = new WorldGenerator(seed, WIDTH, HEIGHT);
        floor = yeet.floor();
        wall = yeet.wall();
        return yeet.world();
    }

    public TETile[][] runThrough(String input) {
        long seed = Long.valueOf(input.substring(1, input.indexOf('s')));
        String s = input.substring(1, input.indexOf('s'));
        SEED.append(s);
        WorldGenerator ww = new WorldGenerator(seed, WIDTH, HEIGHT);
        floor = ww.floor();
        wall = ww.wall();
        w = ww.world();
        Random r = new Random(100);
        p1 = new Avatar(w, r, WIDTH, HEIGHT, true, floor, wall);
        p2 = new Avatar(w, r, WIDTH, HEIGHT, false, floor, wall);
        for (int i = input.indexOf('s') + 1; i < input.length(); i++) {
            char c = input.charAt(i);
            System.out.println(c);
            control(c);
        }
        return w;
    }

    public void control(char c) {
        if (c == 'w') {
            prev = false;
            p1.moveUp();
            savedString = savedString + "w";
        } else if (c == 's') {
            prev = false;
            p1.moveDown();
            savedString = savedString + "s";
        } else if (c == 'a') {
            prev = false;
            p1.moveLeft();
            savedString = savedString + "a";
        } else if (c == 'd') {
            prev = false;
            p1.moveRight();
            savedString = savedString + "d";
        }
        if (c == 'i') {
            prev = false;
            p2.moveUp();
            savedString = savedString + "i";
        } else if (c == 'k') {
            prev = false;
            p2.moveDown();
            savedString = savedString + "k";
        } else if (c == 'j') {
            prev = false;
            p2.moveLeft();
            savedString = savedString + "j";
        } else if (c == 'l') {
            prev = false;
            p2.moveRight();
            savedString = savedString + "l";
        }
        if (c == 't') {
            prev = false;
            newWorld();
            savedString = savedString + "t";
        }

        if (c == ':') {
            if (prev) {
                prev = false;
            } else {
                prev = true;
            }
            savedString = savedString + ":";
        }
        if (c == 'q' && prev) {
            savedString = savedString + "q";
            saveGame();
        }
    }
}
