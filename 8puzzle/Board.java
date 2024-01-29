import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] boardArray;
    private int zeroY, zeroX;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        boardArray = tiles.clone();
        for (int i = 0; i < boardArray.length; i++)
            boardArray[i] = tiles[i].clone();


        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] == 0) {
                    zeroY = y;
                    zeroX = x;
                }
            }
        }
        // System.out.println("[DEBUGGING] Board is: \n" + toString());
    }

    // string representation of this board
    public String toString() {
        String boardString = "";
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                boardString += boardArray[i][j] + " ";
            }
            boardString += (i != dimension() - 1) ? "\n" : "";
        }
        return boardString;
    }

    // board dimension n
    public int dimension() {
        return boardArray.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingNumber = 0;
        int driver = 1;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (boardArray[i][j] != driver)
                    hammingNumber++;
                driver++;
            }

        }
        return hammingNumber;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattenSum = 0;
        for (int y1 = 0; y1 < boardArray.length; y1++) {
            for (int x1 = 0; x1 < boardArray.length; x1++) {
                if (boardArray[y1][x1] != 0) {
                    int[] target = getNumberCoordinates(boardArray[y1][x1]);
                    manhattenSum += Math.abs(target[0] - y1) + Math.abs(target[1] - x1);
                }
            }
        }
        return manhattenSum;
    }

    private int[] getNumberCoordinates(int number) {
        int[] coordinates = new int[2];
        int n = boardArray.length;
        double coordinateUnparsed = number / n;
        coordinates[0] = (int) Math.floor(coordinateUnparsed);
        coordinates[1] = (int) (n * (coordinateUnparsed - coordinates[0]));
        return coordinates;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board boardY = (Board) y;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (boardY.boardArray[i][j] != this.boardArray[i][j])
                    return false;
            }

        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighborsList = new ArrayList<>();
        if (zeroX - 1 >= 0) {
            int[][] neighborArray = boardArray.clone();
            for (int i = 0; i < neighborArray.length; i++)
                neighborArray[i] = boardArray[i].clone();

            int temp = neighborArray[zeroY][zeroX];
            neighborArray[zeroY][zeroX] = neighborArray[zeroY][zeroX - 1];
            neighborArray[zeroY][zeroX - 1] = temp;
            neighborsList.add(new Board(neighborArray));
        }
        if (zeroX + 1 < boardArray.length) {
            int[][] neighborArray = boardArray.clone();
            for (int i = 0; i < neighborArray.length; i++)
                neighborArray[i] = boardArray[i].clone();

            int temp = neighborArray[zeroY][zeroX];
            neighborArray[zeroY][zeroX] = neighborArray[zeroY][zeroX + 1];
            neighborArray[zeroY][zeroX + 1] = temp;
            neighborsList.add(new Board(neighborArray));
        }
        if (zeroY - 1 >= 0) {
            int[][] neighborArray = boardArray.clone();
            for (int i = 0; i < neighborArray.length; i++)
                neighborArray[i] = boardArray[i].clone();

            int temp = neighborArray[zeroY][zeroX];
            neighborArray[zeroY][zeroX] = neighborArray[zeroY - 1][zeroX];
            neighborArray[zeroY - 1][zeroX] = temp;
            neighborsList.add(new Board(neighborArray));
        }
        if (zeroY + 1 < boardArray.length) {
            int[][] neighborArray = boardArray.clone();
            for (int i = 0; i < neighborArray.length; i++)
                neighborArray[i] = boardArray[i].clone();

            int temp = neighborArray[zeroY][zeroX];
            neighborArray[zeroY][zeroX] = neighborArray[zeroY + 1][zeroX];
            neighborArray[zeroY + 1][zeroX] = temp;
            neighborsList.add(new Board(neighborArray));
        }

        return neighborsList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In();
        int n = in.readInt();
        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);
        List<Board> initialNeighbors = (List<Board>) initial.neighbors();
        System.out.println("The board is: \n" + initial.toString());
        System.out.println("The hamming of the board is: " + initial.hamming());
        System.out.println("The manhatten sum of the board is: " + initial.manhattan());
        System.out.println("Is the board solved ? " + initial.isGoal());
        System.out.println("It has " + initialNeighbors.size() + " neighbors");
        int index = 1;
        for (Board b : initialNeighbors) {
            System.out.println("Neighbor " + index + " is: \n" + b.toString());
            index++;
        }
    }

}
