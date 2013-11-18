package btp500.a2;

import java.awt.*;
import java.util.*;
import java.util.List;


public class State<V>
    implements Iterable<Map.Entry<Point, V>> {
    
    private final Map<Point, V> puzzle = new LinkedHashMap<>();

    private Point lastPos;

    private int size;


    public State() {
    }

    public State(int aSize) {
//        System.out.println("State.State");
        size = aSize;
//        System.out.println("size: " + size);
    }

    public State(Map<Point, Integer> aPuzzle, int aSize, Point aLastPos) {
//        System.out.println("State.State");

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

    public boolean isFull() {
//        System.out.println("State.isFull");
//        System.out.println("size*size: " + size*size);
//        System.out.println("puzzleSize: " + puzzle.size());
        return size*size <= puzzle.size();
    }

    public Point hasNum(V num) {
//        System.out.println("State.hasNum");
//        System.out.println("num: " + num);
        Iterator<Map.Entry<Point, V>> it = iterator();
        while (it.hasNext()) {
            Map.Entry<Point, V> item = it.next();
            V value = item.getValue();
//            System.out.println("value: " + value);
            if (value.equals(num)) {
                return item.getKey();
            }
        }
        return null;
    }

    public boolean isAdjacent(Point pos1, Point pos2) {
//        System.out.println("State.isAdjacent");
//        System.out.println("pos1: " + pos1);
//        System.out.println("pos2: " + pos2);
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

    public List<Point> findValidMoves(Point pos) {
//        System.out.println("State.findValidMoves");
//        System.out.println("pos: " + pos);
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
            '}';
    }
}
