package byow.lab13;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round = 1;
    private Random rand;
    private String string;
    private boolean gameOver;
    int addToScore = 5;
    int score = 0;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();

    }

    public MemoryGame(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.WHITE);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random();

    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char letter = CHARACTERS[rand.nextInt(round)];
            stringBuilder.append(letter);
        }
        return stringBuilder.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.text(20, 20, s);
        menu();
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i = 0; i < letters.length(); i++) {
            String charachter = Character.toString(letters.charAt(i));
            drawFrame(charachter);
            delay(1000);
            drawFrame("");
            StdDraw.show();
            delay(1000);
        }
    }

    public void delay(int mili) {
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        drawFrame("Enter Word: ");
        StringBuilder letters = new StringBuilder();
        while(n != 0) {
            waitForKeyStroke();
            if(StdDraw.hasNextKeyTyped()) {
                char keyPressed = StdDraw.nextKeyTyped();
                letters.append(keyPressed);
                drawFrame("Enter Word: " + letters.toString());
                n--;
            }
        }
        return letters.toString();
    }

    public void waitForKeyStroke() {
        while(!StdDraw.hasNextKeyTyped()) {
            delay(100);
        }
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Engine loop
        gameOver = false;
        while(!gameOver){
            drawFrame("Round " + round);
            delay(300);
            string = generateRandomString(round);
            flashSequence(string);
            String user = solicitNCharsInput(round);
            System.out.println(string);
            if(user.equals(string)) {
                Font winFont = new Font("Monaco", Font.BOLD, 15);
                delay(2000);
                drawFrame("You Won Round " + round);
                score += addToScore;
                if(round % 2 == 0) {
                    addToScore +=5;
                }
                delay(4000);
                round++;
            } else {
                drawFrame("Game Over! You Made It To Round: " + round);
                gameOver = true;
            }
        }
    }

    public void menu() {
        Font menuFont = new Font("Monaco", Font.ITALIC, 20);
        StdDraw.setFont(menuFont);
        if(!gameOver) {
            StdDraw.line(0, 38, 40, 38);
            StdDraw.text(3, 39, "Round " + round);
            StdDraw.text( 32, 39, "Score: " + score);
        }
    }

}