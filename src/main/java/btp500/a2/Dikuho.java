package btp500.a2;


import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dikuho {

    private Space<State> space;

    private int counter;

    public Dikuho() {
        System.out.println("Dikuho.Dikuho");

        counter = 0;

        space = new Space<>();

        State<Integer> initState = new State(5);
        // row 0
        initState.set(0,2,13);
        initState.set(0,3,9);

        // row 1
        initState.set(1,1,1);
        initState.set(1,2,8);
        initState.set(1,4,10);

        // row 2
        initState.set(2,0,17);
        initState.set(2,2,2);
        initState.set(2,4,4);

        // row 3
        initState.set(3,1,21);
        initState.set(3,2,6);
        initState.set(3,4,25);

        // row 4
        initState.set(4,0,20);
        initState.set(4,2,22);

        // set coords of last num in the puzzle (1)
        initState.setLastPos(1, 1);
        space.push(initState);
    }

    public boolean solve() {
        System.out.println("\n----> " + counter + " - Dikuho.solve");
//        System.out.println("space size: " + space.size());

        counter++;
        int max = 60;
        if (counter > max) {
            System.out.println("REACHED MAX TRIES - " + max);
            System.out.println("space size: " + space.size());
            return false;
        }

        if (space.isEmpty()) {
            System.out.println("space IS EMPTY");
            return false;
        }

        State<Integer> state = space.pop();
        if (state.isFull()) {
            System.out.println("\n---->state IS FULL");
            state.printPuzzle();
//            System.out.println("size: " + state.getSize() * state.getSize());
//            System.out.println("puzzle size: " + state.getPuzzle().size());
//            Iterator<Map.Entry<Point, Integer>> it = state.iterator();
//            while (it.hasNext()) {
//                Map.Entry<Point, Integer> item = it.next();
//                Point point = item.getKey();
//                Integer value = item.getValue();
//                System.out.println("[" + point.x + ", " + point.y + "] - " + value);
//            }
            return true;
        }



        Point lastPos = state.getLastPos();
        Integer lastNum = state.getLastNum();
        if (lastNum == state.getSize() * state.getSize()) {
            System.out.println("\n---->REACHED THE END - " + lastNum);
            state.printPuzzle();
            return true;
        }

        Integer nextNum = lastNum + 1;
//        System.out.println("--->lastNum: " + lastNum + " - nextNum: " + nextNum);
        Point nextPos = state.hasNum(nextNum);
//        System.out.println("--->lastPos: " + lastPos + " - nextPos: " + nextPos);
        if (nextPos != null) {
//            System.out.println("puzzle already has number " + nextNum);
            // Next num already exists in the puzzle and it is not adjacent to the lastNum
            if (!state.isAdjacent(lastPos, nextPos)) {
//                System.out.println("nextNum: " + nextNum + " is NOT adjacent to lastNum: " + lastNum + " RETURNING FALSE!");
                return solve();
            } else {
//                System.out.println("nextNum: " + nextNum + " is adjacent to lastNum: " + lastNum);
//                System.out.println("nextPos - [" + nextPos.x + ", " + nextPos.y + "]");
                State newState = new State(state.getPuzzle(), state.getSize(), nextPos);
//                System.out.println("newState: " + newState);
                space.push(newState);
                return solve();
            }
        }

        List<Point> moves = state.findValidMoves(lastPos);
//        System.out.println("moves: " + moves);

        for (Point move : moves) {
            State newState = new State(state.getPuzzle(), state.getSize(), move);
//            System.out.println("move - [" + move.x + ", " + move.y + "]");
            newState.set(move.x, move.y, nextNum);
//            System.out.println("newState: " + newState);
            space.push(newState);
        }

        return solve();
    }
}
