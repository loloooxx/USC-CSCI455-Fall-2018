/**
 VisibleField class
 This is the data that's being displayed at any one point in the game (i.e., visible field, because it's what the
 user can see about the minefield), Client can call getStatus(row, col) for any square.
 It actually has data about the whole current state of the game, including
 the underlying mine field (getMineField()).  Other accessors related to game status: numMinesLeft(), isGameOver().
 It also has mutators related to moves the player could do (resetGameDisplay(), cycleGuess(), uncover()),
 and changes the game state accordingly.

 It, along with the MineField (accessible in mineField instance variable), forms
 the Model for the game application, whereas GameBoardPanel is the View and Controller, in the MVC design pattern.
 It contains the MineField that it's partially displaying.  That MineField can be accessed (or modified) from
 outside this class via the getMineField accessor.
 */
public class VisibleField {
    // ----------------------------------------------------------
    // The following public constants (plus numbers mentioned in comments below) are the possible states of one
    // location (a "square") in the visible field (all are values that can be returned by public method
    // getStatus(row, col)).

    // Covered states (all negative values):
    public static final int COVERED = -1;   // initial value of all squares
    public static final int MINE_GUESS = -2;
    public static final int QUESTION = -3;

    // Uncovered states (all non-negative values):

    // values in the range [0,8] corresponds to number of mines adjacent to this square

    public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already (end of losing game)
    public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of losing game
    public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused you to lose)

    private MineField mineField;
    private int[][] squareState; // a 2D array stores states of each square
    private int numRows;  // number of rows of minefield
    private int numCols;  // number of columns of minefield
    private int numMineGuess;  // the times of guessing
    private boolean isGomeOver;  //  check the game is over of not true means over while false means not

    /**
     Create a visible field that has the given underlying mineField.
     The initial state will have all the mines covered up, no mines guessed, and the game
     not over.

     @param mineField  the minefield to use for this VisibleField
     */
    public VisibleField(MineField mineField) {
        this.mineField = mineField;
        numRows = mineField.numRows();
        numCols = mineField.numCols();
        numMineGuess = 0;  // no mine guessing at the beginning
        isGomeOver = false;  // the game is not over at the begining

        squareState = new int[numRows][numCols];


        // The initial state with all the aquares covered
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                squareState[i][j] = COVERED;
            }
        }
    }


    /**
     Reset the object to its initial state (see constructor comments), using the same underlying MineField.
     */
    public void resetGameDisplay() {

        numMineGuess = 0;  // no mine guessing at the beginning
        isGomeOver = false;  // the game is not over at the begining

        // The initial state with all the aquares covered
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                squareState[i][j] = COVERED;
            }
        }
    }


    /**
     Returns a reference to the mineField that this VisibleField "covers"

     @return the minefield
     */
    public MineField getMineField() {
        return mineField;
    }


    /**
     get the visible status of the square indicated.

     @param row  row of the square
     @param col  col of the square
     @return the status of the square at location (row, col).  See the public constants at the beginning of the class
     for the possible values that may be returned, and their meanings.

     PRE: getMineField().inRange(row, col)
     */
    public int getStatus(int row, int col) {

        if (getMineField().inRange(row, col)) {
            return squareState[row][col];
        }

        // row and col must be in the minefield. if not, return max value of integer. This situation will not happen.
        return Integer.MAX_VALUE;
    }


    /**
     Return the the number of mines left to guess.  This has nothing to do with whether the mines guessed are correct
     or not.  Just gives the user an indication of how many more mines the user might want to guess.  So the value can
     be negative, if they have guessed more than the number of mines in the minefield.

     @return the number of mines left to guess.
     */
    public int numMinesLeft() {

        // number of left mines = number of total mines - number of mine guessing
        return getMineField().numMines() - numMineGuess;

    }


    /**
     Cycles through covered states for a square, updating number of guesses as necessary.  Call on a COVERED square
     changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to QUESTION;  call on a QUESTION square
     changes it to COVERED again; call on an uncovered square has no effect.

     @param row  row of the square
     @param col  col of the square

     PRE: getMineField().inRange(row, col)
     */
    public void cycleGuess(int row, int col) {

        if (getMineField().inRange(row, col)) {

            if (squareState[row][col] == COVERED) {

                // Call on a COVERED square changes its status to MINE_GUESS and number of mine guessing plus one
                squareState[row][col] = MINE_GUESS;
                numMineGuess++;

            } else if (squareState[row][col] == MINE_GUESS) {

                // call on a MINE_GUESS square changes it to QUESTION and number of mine guessing minus one
                squareState[row][col] = QUESTION;
                numMineGuess--;

            } else {

                // call on a QUESTION square changes it to COVERED
                squareState[row][col] = COVERED;
            }
        }
    }


    /**
     Uncovers this square and returns false iff you uncover a mine here.
     If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in
     the neighboring area that are also not next to any mines, possibly uncovering a large region.
     Any mine-adjacent squares you reach will also be uncovered, and form
     (possibly along with parts of the edge of the whole field) the boundary of this region.
     Does not uncover, or keep searching through, squares that have the status MINE_GUESS.

     @param row  row of the square
     @param col  col of the square
     @return false   iff you uncover a mine at (row, col)

     PRE: getMineField().inRange(row, col)
     */
    public boolean uncover(int row, int col) {

        if (getMineField().inRange(row, col)) {

            // uncover a square which there is a mine on it
            if (getMineField().hasMine(row, col)) {

                // set the game is over
                isGomeOver = true;

                // update states of each square when losing
                unpdateLoseStatus(row, col);

                return false;
            }

            // uncover a square which there is no mine on it.
            // the game will keep uncovering adjacent squares until it gets to the boundary of the field, squares that are adjacent to other                    // mines or the state of squares are MINE_GUESS or QUESTION
            keepUncover(row, col);

            // check if every aquares are covered. true means the game is over, the player wins
            if (isAllDone()) {

                // update states of each square when winning
                unpdateWinStatus(row, col);
                isGomeOver = true;
            }

            return true;
        }

        return false; // row and col must be in the minefield. if not, return false
    }


    /**
     Returns whether the game is over.

     @return whether game over
     */
    public boolean isGameOver() {
        return isGomeOver;
    }


    /**
     Return whether this square has been uncovered.  (i.e., is in any one of the uncovered states,
     vs. any one of the covered states).

     @param row of the square
     @param col of the square
     @return whether the square is uncovered

     PRE: getMineField().inRange(row, col)
     */
    public boolean isUncovered(int row, int col) {

        if (getMineField().inRange(row, col)) {

            // if states of each square is COVERED, MINE_GUESS or QUESTION, return false
            if (QUESTION <= squareState[row][col] && squareState[row][col] <= COVERED) {
                return false;
            }

            // if state of each square is [0, 8], means this square is already uncovered
            if (0 <= squareState[row][col] && squareState[row][col] <= 8) {
                return true;
            }
        }

        return false; // row and col must be in the minefield. if not, return false

    }


    // <put private methods here>


    /**
     keep uncovering adjacent squares by recursion. Recursion will stop until it gets to the boundary of the field, squares that are                     adjacent to other squares or the state of squares are MINE_GUESS or QUESTION

     @param row  row of the square
     @param col  col of the square

     PRE: getMineField().inRange(row, col)
     */
    private void keepUncover(int row, int col) {

        if (getMineField().inRange(row, col)) {

            // if squares are uncovered or its states is MINE_GUESS, stop the recursion
            if ((0 <= squareState[row][col] && squareState[row][col] <= 8) || squareState[row][col] == MINE_GUESS) {

                return;

            } else {

                // check the square has how many adjacent mines. the value would be in [0, 8] and update the state of this square
                squareState[row][col] = getMineField().numAdjacentMines(row, col);

                // if the square has no adjacent mines, keep cheching its adjacent squares have adjacent mines or not
                if (squareState[row][col] == 0) {
                    keepUncover(row - 1, col - 1);
                    keepUncover(row - 1, col);
                    keepUncover(row - 1, col + 1);
                    keepUncover(row, col - 1);
                    keepUncover(row, col + 1);
                    keepUncover(row + 1, col - 1);
                    keepUncover(row + 1, col);
                    keepUncover(row + 1, col + 1);
                }

                return;

            }
        }
        return;
    }


    /**
     Check all the squares which there is no mine on it are covered up ot not. If yes, return true. If not, return false.

     @return whether all the squares are covered up or on the right states
     */
    private boolean isAllDone() {

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                // all the squares should be uncovered except there is a mine on it
                if (squareState[row][col] == COVERED && !getMineField().hasMine(row, col)) {
                    return false;
                }

                // if the square is on QUESTION state, it must have a mine
                if (squareState[row][col] == QUESTION && !getMineField().hasMine(row, col)) {
                    return false;
                }

                // the square should be guessed right
                if (squareState[row][col] == MINE_GUESS && !getMineField().hasMine(row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     Update all states on all the square when losing

     @param row  row of the square
     @param col  col of the square

     PRE: getMineField().inRange(row, col)
     */
    private void unpdateLoseStatus(int row, int col) {

        if (getMineField().inRange(row, col)) {

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {

                    // player clicks on a mine, the state of this square turns to EXPLODED_MINE
                    if (i == row && j == col) {

                        squareState[i][j] = EXPLODED_MINE;

                        // the state of the square is MINE_GUESS and there is a mine on it. its state is still MINE_GUESS
                    } else if ((squareState[i][j] == MINE_GUESS) && (getMineField().hasMine(i, j))) {

                        squareState[i][j] = MINE_GUESS;

                        // the state of the square is MINE_GUESS and there is no mine on it. its state turns to INCORRECT_GUESS
                    } else if ((squareState[i][j] == MINE_GUESS) && (!getMineField().hasMine(i, j))) {

                        squareState[i][j] = INCORRECT_GUESS;

                        // the state of the square is COVERED and there is a mine on it. its state turns to MINE
                    } else if ((squareState[i][j] == COVERED) && (getMineField().hasMine(i, j))) {

                        squareState[i][j] = MINE;

                        // the state of square is QUESTIO and there is no mine on it. its state is still QUESTIO
                    } else if ((squareState[i][j] == QUESTION) && (!getMineField().hasMine(i, j))) {
                        squareState[i][j] = QUESTION;

                        // the state of square is QUESTIO and there is a mine on it. its state turns to MINE
                    } else if ((squareState[i][j] == QUESTION) && (getMineField().hasMine(i, j))) {
                        squareState[i][j] = MINE;
                    }
                }
            }
        }
    }


    /**
     Update all states on all the square when winning

     @param row  row of the square
     @param col  col of the square

     PRE: getMineField().inRange(row, col)
     */
    private void unpdateWinStatus(int row, int col) {

        if (getMineField().inRange(row, col)) {

            // When winning, all the squares should be uncovered up. If not, there must be a mine on it. Make its state turn to MINE_GUESS
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    if (getMineField().hasMine(i, j)) {
                        squareState[i][j] = MINE_GUESS;
                    }
                }
            }
        }
    }

}
