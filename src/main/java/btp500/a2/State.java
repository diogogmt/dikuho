package btp500.a2;

import java.awt.*;
import java.util.*;
import java.util.List;


public class State<V>
    implements Iterable<Map.Entry<Point, V>> {

    /*
        @puzzle - Implementation of 2D array
        @Point represents a x,y coordinates on the plane
        @V holds the value stored on the coordinate
     */
    private final Map<Point, V> puzzle = new LinkedHashMap<>();

    /*
        @lastPos
        Coordinates of the last move performed on the puzzle
     */
    private Point lastPos;

    /*
        @size
        flat size of the puzzle (width/height)
     */
    private int size;

    /*
        @depth
        debug attribute to keep track of how many levels the state is deep on the space
     */
    private int depth;


    public State() {
    }

    /*
        Initializes a State with size^2 tiles
     */
    public State(int aSize) {
        size = aSize;
    }

    /*
        Copy constructor for State
        Initializes a new state with the puzzle, size and lastPos of an existing State
     */
    public State(Map<Point, Integer> aPuzzle, int aSize, Point aLastPos) {
        size = aSize;
        lastPos = aLastPos;

        Iterator<Map.Entry<Point,Integer>> it = aPuzzle.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Point, Integer> item = it.next();
            Point point = item.getKey();
            Integer value = item.getValue();
            this.set(point.x, point.y, (V) value);
        }
    }

    public V set(int x, int y, V value) {
        return puzzle.put(new Point(x,y), value);
    }
    
    public V get(int x, int y) {
        return puzzle.get(new Point(x, y));
    }
    
    public Iterator<Map.Entry<Point, V>> iterator() {
        return puzzle.entrySet().iterator();
    }

    /*
        Checks if puzzle is finished
        If the size of the puzzle (number of tiles) equals size^2 means all tiles are filled
     */
    public boolean isFull() {
        return size*size <= puzzle.size();
    }

    /*
        Check if the puzzle contains a number n
     */
    public Point hasNum(V num) {
        Iterator<Map.Entry<Point, V>> it = iterator();
        while (it.hasNext()) {
            Map.Entry<Point, V> item = it.next();
            V value = item.getValue();
            if (value.equals(num)) {
                return item.getKey();
            }
        }
        return null;
    }

    /*
        Checks if pos1 is adjacent to pos2
        Algorithm iterates on all possible 9 valid coords:
        -1, -1
        -1 ,0
        -1, +1
        0, 0
        0, -1
        0, +1
        +1, 0
        +1, -1
        +1, +1
        And adds them to pos2
        If they match means they are adjacent
     */
    public boolean isAdjacent(Point pos1, Point pos2) {
        for (int i = -1; i <= 1; i++) {
            int x = pos2.x;
            x += i;
            for (int j = -1; j <= 1; j++) {
                int y = pos2.y;
                y += j;
                if (pos1.x == x && pos1.y == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        Gather all possible valid moves for a selected position
        A Valid move is defined by a adjacent tile that does not contain a number
     */
    public List<Point> findValidMoves(Point pos) {
        List<Point> moves = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            int x = pos.x;
            x += i;
            for (int j = -1; j <= 1; j++) {
                int y = pos.y;
                y += j;
                Point point = new Point(x, y);
                if (!puzzle.containsKey(point) && x >= 0 && x <= size - 1 && y >= 0 && y <= size - 1) moves.add(point);
            }
        }
        return moves;
    }


    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point getLastPos() {
        return lastPos;
    }

    public void setLastPos(Point lastPos) {
        this.lastPos = lastPos;
    }

    public void setLastPos(int x, int y) {
        this.lastPos = new Point(x, y);
    }

    public Map<Point, V> getPuzzle() {
        return puzzle;
    }

    public V getLastNum() {
        return puzzle.get(lastPos);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void printPuzzle() {
        for (int x = 0; x < size; x++) {
            System.out.print("\n__________________________\n");
            System.out.print("| ");
            for (int y = 0; y < size; y++) {
                Point point = new Point(x, y);
                if (puzzle.containsKey(point)) {
                    V item = puzzle.get(point);
                    System.out.print(String.format("%02d", item) + " | ");
                } else {
                    System.out.print("00 | ");
                }
            }
        }
        System.out.print("\n_________________________\n");
    }

    @Override
    public String toString() {
        printPuzzle();
        return "State{" +
            ", lastPos=" + lastPos +
            ", size=" + size +
            ", depth=" + depth +
            '}';
    }
}
