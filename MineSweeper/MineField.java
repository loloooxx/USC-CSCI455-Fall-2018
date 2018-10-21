/**
 MineField
 class with locations of mines for a game.
 This class is mutable, because we sometimes need to change it once it's created.
 mutators: populateMineField, resetEmpty
 includes convenience method to tell the number of mines adjacent to a location.
 */

import java.util.Random;

public class MineField {

    private int numRows; // number of rows of minefield
    private int numCols; // number of columns of minefield
    private int numMines; // number of mines on minefield
    private int numMinesGenerationLeft; // number of left mines should be generated when generating mines
    private boolean[][] field; // a 2D array to stores states of each square
    private Random generator;

    /**
     Create a minefield with same dimensions as the given array, and populate it with the mines in the array
     such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
     this minefield will corresponds to the number of 'true' values in mineData.

     @param mineData  the data for the mines; must have at least one row and one col.
     */
    public MineField(boolean[][] mineData) {
        numRows = mineData.length;
        numCols = mineData[0].length;
        field = mineData;

        // count how many mines on the minefiled
        int num = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (field[i][j] == true) {
                    num++;
                }
            }
        }

        numMines = num;
    }


    /**
     Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once
     populateMineField is called on this object).  Until populateMineField is called on such a MineField,
     numMines() will not correspond to the number of mines currently in the MineField.

     @param numRows  number of rows this minefield will have, must be positive
     @param numCols  number of columns this minefield will have, must be positive
     @param numMines   number of mines this minefield will have,  once we populate it.

     PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations).
     */
    public MineField(int numRows, int numCols, int numMines) {

        assert numRows > 0 && numCols > 0;
        int limit = numRows * numCols;
        assert numMines < limit / 3.0;

        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        numMinesGenerationLeft = numMines; // there are numMines mines to guess, and no mine guessing at the beginning
        field = new boolean[numRows][numCols];
        generator = new Random();
    }


    /**
     Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
     ensuring that no mine is placed at (row, col).

     @param row the row of the location to avoid placing a mine
     @param col the column of the location to avoid placing a mine

     PRE: inRange(row, col)
     */
    public void populateMineField(int row, int col) {

        if (inRange(row, col)) {

            resetEmpty();

            // the game will generate numMines mines
            int numMinesGenerationLeft = numMines;

            while (numMinesGenerationLeft > 0 ) {
                numMinesGenerationLeft--;
                int rowLoc = generator.nextInt(numRows);
                int colLoc = generator.nextInt(numCols);

                // if the mine generates on the first square which the player clicks, it would ignores this generation and numMinesGenerationLeft plus             // one
                if (rowLoc == row && colLoc == col) {
                    numMinesGenerationLeft++;
                    continue;
                }

                // if the mine generates on the duplicate square, it would ignores this generation and numMinesGenerationLeft plus one
                if (field[rowLoc][colLoc]) {
                    numMinesGenerationLeft++;
                    continue;
                }

                // generate mines on the random squares
                field[rowLoc][colLoc] = true;

            }
        }
    }


    /**
     Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
     Thus, after this call, the actual number of mines in the minefield does not match numMines().
     Note: This is the state the minefield is in at the beginning of a game.
     */
    public void resetEmpty() {

        // there is no mines on the mindfiels at the beginning, so it turns every squares into false
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++){
                field[i][j] = false;
            }
        }
    }


    /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     There are nine situations which the player would click including (1)center of field, (2)top center of field, (3)bottom center of field,
     (4)left center of field, (5)right center of field, (6)upper left corner of field, (7)upper right corner of field,
     (8)lower left corner of field and (9)lower right corner of field

     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)

     PRE: inRange(row, col)
     */
    public int numAdjacentMines(int row, int col) {

        if (inRange(row, col)) {

            // count how many mines on adjacent squares. num is cumulative
            int num = 0;

            // the first situation: the user clicks on center of field, so it needs to check eight adjacent squares
            if ((row > 0 && row < numRows - 1) && (col > 0 && col < numCols - 1)) {
                num = checkUpperLeft(row, col, num);
                num = checkUpper(row, col, num);
                num = checkUpperRight(row, col, num);
                num = checkLeft(row, col, num);
                num = checkRight(row, col, num);
                num = checkLowerLeft(row, col, num);
                num = checkLower(row, col, num);
                num = checkLowerRight(row, col, num);
            }

            // the second situation: the user clicks on top center of field, so it needs to check five adjacent squares
            if ((row == 0) && (col > 0 && col < numCols - 1)) {
                num = checkLeft(row, col, num);
                num = checkRight(row, col, num);
                num = checkLowerLeft(row, col, num);
                num = checkLower(row, col, num);
                num = checkLowerRight(row, col, num);
            }

            // the third situation: the user clicks on bottom center of field, so it needs to check five adjacent squares
            if ((row == numRows - 1) && (col > 0 && col < numCols - 1)) {
                num = checkUpperLeft(row, col, num);
                num = checkUpper(row, col, num);
                num = checkUpperRight(row, col, num);
                num = checkLeft(row, col, num);
                num = checkRight(row, col, num);
            }

            // the forth situation: the user clicks on left center of field, so it needs to check five adjacent squares
            if ((row > 0 && row < numRows - 1) && (col == 0)) {
                num = checkUpper(row, col, num);
                num = checkUpperRight(row, col, num);
                num = checkRight(row, col, num);
                num = checkLower(row, col, num);
                num = checkLowerRight(row, col, num);
            }

            // the fifth situation: the user clicks on right center of field, so it needs to check five adjacent squares
            if ((row > 0 && row < numRows - 1) && (col == numCols - 1)) {
                num = checkUpperLeft(row, col, num);
                num = checkUpper(row, col, num);
                num = checkLeft(row, col, num);
                num = checkLowerLeft(row, col, num);
                num = checkLower(row, col, num);
            }

            // the sixth situation: the user clicks on upper left corner of field, so it needs to check three adjacent squares
            if (row == 0 && col == 0) {
                num = num = checkRight(row, col, num);
                num = checkLower(row, col, num);
                num = checkLowerRight(row, col, num);
            }

            // the seventh situation: the user clicks on upper right corner of field, so it needs to check three adjacent squares
            if (row == 0 && col == numCols - 1) {
                num = checkLeft(row, col, num);
                num = checkLowerLeft(row, col, num);
                num = checkLower(row, col, num);
            }

            // the eighth situation: the user clicks on lower left corner of field, so it needs to check three adjacent squares
            if (row == numRows - 1 && col == 0) {
                num = checkUpper(row, col, num);
                num = checkUpperRight(row, col, num);
                num = checkRight(row, col, num);
            }

            // the ninth situation: the user clicks on lower right corner of field, so it needs to check three adjacent squares
            if (row == numRows - 1 && col == numCols - 1) {
                num = checkUpperLeft(row, col, num);
                num = checkUpper(row, col, num);
                num = checkLeft(row, col, num);
            }

            return num;
        }

        // row and col must be in the minefield. if not, return zero. This situation will not happen.
        return 0;
    }


    /**
     Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
     start from 0.

     @param row  row of the location to consider
     @param col  column of the location to consider

     @return whether (row, col) is a valid field location
     */
    public boolean inRange(int row, int col) {

        if ((row >= 0 && row < numRows) && (col >= 0) && (col < numCols)) {
            return true;
        }

        return false;
    }


    /**
     Returns the number of rows in the field.

     @return number of rows in the field
     */
    public int numRows() {
        return numRows;
    }


    /**
     Returns the number of columns in the field.

     @return number of columns in the field
     */
    public int numCols() {
        return numCols;
    }


    /**
     Returns whether there is a mine in this square

     @param row  row of the location to check
     @param col  column of the location to check
     @return whether there is a mine in this square

     PRE: inRange(row, col)
     */
    public boolean hasMine(int row, int col) {

        if (inRange(row, col)) {
            if (field[row][col] == true) {
                return true;  // return true means there is a mine in this square
            }
        }

        return false;  // return false means there is no mine in this square or this square is invalid
    }


    /**
     Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
     some of the time this value does not match the actual number of mines currently on the field.  See doc for that
     constructor, resetEmpty, and populateMineField for more details.

     * @return the number of mines
     */
    public int numMines() {
        return numMines;
    }


    // <put private methods here>


    /**
     Returns whether there is a mine on the upper left of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkUpperLeft(int row, int col, int numAdjoinMines) {
        if (field[row - 1][col - 1] == true) {
            numAdjoinMines++;
        }

        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the upper of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkUpper(int row, int col, int numAdjoinMines) {
        if (field[row - 1][col] == true) {
            numAdjoinMines++;
        }

        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the upper right of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkUpperRight(int row, int col, int numAdjoinMines) {
        if (field[row - 1][col + 1] == true) {
            numAdjoinMines++;
        }

        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the left of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkLeft(int row, int col, int numAdjoinMines) {
        if (field[row][col - 1] == true) {
            numAdjoinMines++;
        }

        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the right of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkRight(int row, int col, int numAdjoinMines) {
        if (field[row][col + 1] == true) {
            numAdjoinMines++;
        }

        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the lower left of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkLowerLeft(int row, int col, int numAdjoinMines) {
        if (field[row + 1][col - 1] == true) {
            numAdjoinMines++;
        }
        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the lower of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkLower(int row, int col, int numAdjoinMines) {
        if (field[row + 1][col] == true) {
            numAdjoinMines++;
        }
        return numAdjoinMines;
    }

    /**
     Returns whether there is a mine on the lower right of this square. If there is a mine, the numAdjoinMines plus one.
     The numAdjoinMines is cumulative.

     @param row  row of the location to check
     @param col  column of the location to chec
     @numAdjoinMines  tatal number of adjacent mines of this square

      * @return tatal number of adjacent mines of this square
     */
    private int checkLowerRight(int row, int col, int numAdjoinMines) {
        if (field[row + 1][col + 1] == true) {
            numAdjoinMines++;
        }
        return numAdjoinMines;
    }
}

