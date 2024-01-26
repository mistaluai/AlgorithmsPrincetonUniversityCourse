public class Board {

    int[][] boardArray;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        boardArray = tiles;
    }

    // string representation of this board
    public String toString() {
        String boardString = "";
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                boardString += boardArray[i][j] + " ";
            }
            boardString += "\n";
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
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
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
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}
