package game2048;

import java.util.Formatter;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The state of a game of 2048.
 *
 * @author Colin McNichols
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    public boolean tiltColumn(int col) {
        boolean changed = false;
        Board b = this.board;
        ArrayList<Integer> null_indices = new ArrayList<>();
        ArrayList<Integer> num_indices = new ArrayList<>();

        for (int row = 0; row < b.size(); row++) {

            if (b.tile(col, row) == null) {
                null_indices.add(row);

            } else {
                num_indices.add(row);

            }
        }

        for (int element = num_indices.size() - 1; element > 0; ) {
            Tile current = b.tile(col,num_indices.get(element));
            Tile neighbour = b.tile(col,num_indices.get(element - 1));

            if (current.value() == neighbour.value()) {
                null_indices.add(num_indices.get(element - 1));
                b.move(col, num_indices.get(element), neighbour);
                this.score = this.score +b.tile(col, num_indices.get(element)).value();
                changed = true;
                num_indices.remove(element - 1);

                element--;
            }
            element--;
        }
        System.out.println(b);        

        for (int element = num_indices.size() - 1; element >= 0; element--) {
            System.out.println(null_indices);
            int row = num_indices.get(element);
            Tile current = b.tile(col, row);
            System.out.println("Current Value: " + current.value() + "  Current row: "+ row);

            System.out.println(b);
            if (null_indices.isEmpty()) return changed;
            int max_null_index = Collections.max(null_indices);
            System.out.println("max null: " + max_null_index);
            if (row < max_null_index) {
                null_indices.add(row);
                null_indices.remove(Integer.valueOf(max_null_index));
                b.move(col, max_null_index, current);
                changed = true;
            }

        }

        System.out.println(b);
        return changed;

    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {

        boolean changed;
        changed = false;

        Board b = this.board;
        b.setViewingPerspective(side);     
        int rows = b.size();

        for (int col = 0; col < rows; col++) {





            System.out.println(col);
            System.out.println("_____________________");
            if (tiltColumn(col)) changed = true;
             System.out.println("_____________________");
             System.out.println("_____________________");

        }


        checkGameOver();
        if (changed) {
            setChanged();
        }
        b.setViewingPerspective(Side.NORTH);
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int cols = b.size();
        int rows = cols;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (b.tile(col, row) == null) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int cols = b.size();
        int rows = cols;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                Tile tile = b.tile(col, row);
                if (tile == null) continue;
                if (tile.value() == MAX_PIECE) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) return true;

        int cols = b.size();
        int rows = cols;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int this_value = b.tile(col, row).value();
                if (col < cols - 1) {
                    int right_value = b.tile(col + 1, row).value();
                    if (right_value == this_value) return true;
                }
                if (row < rows - 1) {
                    int down_value = b.tile(col, row + 1).value();
                    if (down_value == this_value) return true;
                }
            }
        }
        return false;
    }




    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
