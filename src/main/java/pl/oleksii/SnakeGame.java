package pl.oleksii;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private int[][] gameBoard;
    private int[] food;
    private ArrayList<int[]> snake;
    private int direction;

    public SnakeGame() {
        gameBoard = new int[WIDTH][HEIGHT];
        snake = new ArrayList<>();
        direction = 1;
        initBoard();
        initFood();
        initSnake();
    }

    // 1 granica(#), 0 pustoe pole, 2 golowa, 3 xwost, 4 eda
    public void initBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (i == 0 || j == 0 || i == WIDTH - 1 || j == HEIGHT - 1) {
                    gameBoard[i][j] = 1;
                } else {
                    gameBoard[i][j] = 0;
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (gameBoard[i][j] == 1) {
                    System.out.print("#");
                } else if (gameBoard[i][j] == 2) {
                    System.out.print("o");
                } else if (gameBoard[i][j] == 3) {
                    System.out.print("x");
                } else if (gameBoard[i][j] == 4) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void initFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH - 2) + 1;
        int y = random.nextInt(HEIGHT - 2) + 1;
        while (gameBoard[x][y] != 0) {
            x = random.nextInt(WIDTH - 2) + 1;
            y = random.nextInt(HEIGHT - 2) + 1;
        }
        food = new int[]{x, y};
        gameBoard[x][y] = 4;
    }

    public void initSnake() {
        int[] head = {5, 10};
        snake.add(head);
        int[] body = {5, 11};
        snake.add(body);
        int[] tail = {5, 12};
        snake.add(tail);
        gameBoard[5][10] = 2;
        gameBoard[5][11] = 3;
        gameBoard[5][12] = 3;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("1-left || 2-up || 3-down || 4-right");
            int input = scanner.nextInt();
            if (input >= 1 && input <= 4) {
                direction = input;
            }
            int[] head = snake.get(0);
            int[] newHead = new int[2];
            if (direction == 1) {
                newHead[0] = head[0];
                newHead[1] = head[1] - 1;
            } else if (direction == 2) {
                newHead[0] = head[0] - 1;
                newHead[1] = head[1];
            } else if (direction == 3) {
                newHead[0] = head[0] + 1;
                newHead[1] = head[1];
            } else if (direction == 4) {
                newHead[0] = head[0];
                newHead[1] = head[1] + 1;
            }
            if (gameBoard[newHead[0]][newHead[1]] == 1 || gameBoard[newHead[0]][newHead[1]] == 3) {
                System.out.println("GameOver!!!");
                break;
            } else if (gameBoard[newHead[0]][newHead[1]] == 4) {
                snake.add(0, newHead);
                gameBoard[newHead[0]][newHead[1]] = 2;
                initFood();
            } else {
                int[] tail = snake.remove(snake.size() - 1);
                gameBoard[tail[0]][tail[1]] = 0;
                snake.add(0, newHead);
                gameBoard[newHead[0]][newHead[1]] = 2;
                for (int i = 1; i < snake.size(); i++) {
                    int[] body = snake.get(i);
                    gameBoard[body[0]][body[1]] = 3;
                }
            }
        }
        scanner.close();
    }
}