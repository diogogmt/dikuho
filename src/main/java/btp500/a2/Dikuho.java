package btp500.a2;


import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Dikuho {

    /*
        @space
        Stack of states
     */
    private Space<State> space;

    private State lastState; // Used for debugging

    private int counter; // Used for debugging

    public Dikuho() {
        counter = 0;
    }

    /*
        Recursive function that pops a state from space and performs operations on the state
        It first checks if the state is complete
        If not it finds all valid moves, builds a new state, pushes to the space and call it self again
     */
    public boolean solve() {

        counter++; // For debugging, keep track of how many iterations were made
        System.out.println(counter + ". - space.size: " + space.size());

        /*
            If the space is empty it means we ran out of moves and didn't find a solution for the puzzle
         */
        if (space.isEmpty()) {
            System.out.println("\n---->Couldn't find solution for Puzzle");
            return false;
        }

        // pops state from space(stack)
        State<Integer> state = space.pop();
        lastState = state;
        /*
            If state is full means the puzzle was completed
            The state would only be full if all tiles were filed with valid numbers
         */
        if (state.isFull()) {
            System.out.println("\n---->Puzzle solved");
            state.printPuzzle();
            return true;
        }

        /*
            Find the last chosen number and its coordinates (x,y)
         */
        Point lastPos = state.getLastPos();
        Integer lastNum = state.getLastNum();
        /*
            If the last chose number equals to the puzzle size means we reached the end
            The algorithm increments the number iteratively, so n would only be selected if n-1 number were
            already placed on the puzzle
         */
        if (lastNum == state.getSize() * state.getSize()) {
            System.out.println("\n---->REACHED THE END - " + lastNum);
            state.printPuzzle();
            return true;
        }

        /*
            Defines the next number to be selected
         */
        Integer nextNum = lastNum + 1;
        /*
            Checks if number already exists in the puzzle
         */
        Point nextPos = state.hasNum(nextNum);
        if (nextPos != null) {
            /*
                Check if selected number is adjacent to the previous number
                Since the algorithm selects number iteratively the last number must be adjacent to the selected number
                or the move is invalid
             */
            if (!state.isAdjacent(lastPos, nextPos)) { // Next num already exists in the puzzle and it is not adjacent to the lastNum
                return solve();
            } else { // Selected number is on a valid position
                /*
                    Creates a new state and pushes to the space
                    Set the lastNum on the new state to the selected number of this round
                 */
                State newState = new State(state.getPuzzle(), state.getSize(), nextPos);
                newState.setDepth(state.getDepth() + 1); // For debugging, keep track of how many levels deep we are in
                space.push(newState);
                return solve();
            }
        }

        /*
            Find all valid tiles where next number can be placed
            A valid tile is a tile that doesn't have a number
         */
        List<Point> moves = state.findValidMoves(lastPos);
        for (Point move : moves) {
            /*
                Creates a new state with the new move and pushes to the space
             */
            State newState = new State(state.getPuzzle(), state.getSize(), move);
            newState.set(move.x, move.y, nextNum);
            newState.setDepth(state.getDepth() + 1); // For debugging, keep track of how many levels deep we are in
            space.push(newState);
        }
        return solve();
    }



    /*
        Test puzzles
     */

    public void initGame1() {
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


    public void initTestGame1() {
        space = new Space<>();

        State<Integer> initState = new State(3);
        // row 0
        initState.set(0,0,9);
        initState.set(0,1,7);

        // row 1
        initState.set(1,0,8);

        // row 2
        initState.set(2,1,2);
        initState.set(2,2,1);



        // set coords of last num in the puzzle (1)
        initState.setLastPos(2, 2);
        initState.setDepth(1);
        space.push(initState);
    }

    public void initTestGame2() {
        space = new Space<>();

        State<Integer> initState = new State(4);
        // row 0
        initState.set(0,0,16);
        initState.set(0,1,1);
        initState.set(0,2,16);

        // row 1
        initState.set(1,3,4);

        // row 2


        // row 3
        initState.set(3,1,12);



        // set coords of last num in the puzzle (1)
        initState.setLastPos(0, 1);
        initState.setDepth(1);
        space.push(initState);
    }

    public void initTestGame3() {
        space = new Space<>();

        State<Integer> initState = new State(5);
        // row 0
        initState.set(0,1,10);
        initState.set(0,2,7);
        initState.set(0,4,4);

        // row 1

        // row 2
        initState.set(2,1,12);
        initState.set(2,3,14);

        // row 3
        initState.set(3,1,1);
        initState.set(3,2,25);
        initState.set(3,4,15);

        // row 4



        // set coords of last num in the puzzle (1)
        initState.setLastPos(3, 1);
        initState.setDepth(1);
        space.push(initState);
    }

    public void initTestGame4() {
        space = new Space<>();

        State<Integer> initState = new State(6);
        // row 0
        initState.set(0,1,23);
        initState.set(0,3,25);
        initState.set(0,4,1);

        // row 1
        initState.set(1,0,32);
        initState.set(1,5,3);

        // row 2
        initState.set(2,1,21);
        initState.set(2,4,11);

        // row 3
        initState.set(3,1,20);
        initState.set(3,4,6);

        // row 4
        initState.set(4,1,19);
        initState.set(4,5,9);

        // row 5
        initState.set(5,0,36);
        initState.set(5,3,14);


        // set coords of last num in the puzzle (1)
        initState.setLastPos(0, 4);
        initState.setDepth(1);
        space.push(initState);
    }

    public void initTestGame5() {
        space = new Space<>();

        State<Integer> initState = new State(7);
        // row 0
        initState.set(0,0,5);
        initState.set(0,1,4);
        initState.set(0,3,36);
        initState.set(0,4,37);
        initState.set(0,6,31);

        // row 1
        initState.set(1,2,1);
        initState.set(1,4,38);
        initState.set(1,5,32);

        // row 2
        initState.set(2,0,9);
        initState.set(2,1,7);
        initState.set(2,2,45);
        initState.set(2,3,39);
        initState.set(2,4,34);

        // row 3
        initState.set(3,3,44);
        initState.set(3,5,41);

        // row 4
        initState.set(4,1,11);
        initState.set(4,2,47);
        initState.set(4,5,21);
        initState.set(4,6,26);

        // row 5
        initState.set(5,0,13);
        initState.set(5,4,20);
        initState.set(5,6,25);

        // row 6
        initState.set(6,1,49);
        initState.set(6,2,16);
        initState.set(6,4,18);
        initState.set(6,5,24);

        // set coords of last num in the puzzle (1)
        initState.setLastPos(1, 2);
        initState.setDepth(1);
        space.push(initState);
    }


    public void initTestGame6() {
        space = new Space<>();

        State<Integer> initState = new State(8);
        // row 0
        initState.set(0,4,38);
        initState.set(0,6,35);
        initState.set(0,7,34);

        // row 1
        initState.set(1,0,3);
        initState.set(1,1,1);

        // row 2
        initState.set(2,1,8);
        initState.set(2,3,60);
        initState.set(2,4,53);

        // row 3
        initState.set(3,3,63);
        initState.set(3,4,59);
        initState.set(3,6,41);

        // row 4
        initState.set(4,1,47);
        initState.set(4,2,64);
        initState.set(4,5,55);

        // row 5
        initState.set(5,1,18);
        initState.set(5,7,26);

        // row 6
        initState.set(6,0,13);
        initState.set(6,5,28);

        // row 7
        initState.set(7,4,21);

        // set coords of last num in the puzzle (1)
        initState.setLastPos(1, 1);
        initState.setDepth(1);
        space.push(initState);
    }


    public void initTestGame7() {
        space = new Space<>();

        State<Integer> initState = new State(9);
        // row 0
        initState.set(0,1,11);
        initState.set(0,5,46);

        // row 1
        initState.set(1,0,1);
        initState.set(1,2,4);
        initState.set(1,6,42);
        initState.set(1,7,44);

        // row 2
        initState.set(2,3,8);
        initState.set(2,8,36);

        // row 3
        initState.set(3,0,57);
        initState.set(3,2,52);

        // row 4
        initState.set(4,1,55);
        initState.set(4,2,53);
        initState.set(4,6,34);
        initState.set(4,7,31);

        // row 5
        initState.set(5,5,19);
        initState.set(5,8,30);

        // row 6
        initState.set(6,0,79);
        initState.set(6,5,65);

        // row 7
        initState.set(7,1,77);
        initState.set(7,2,81);
        initState.set(7,47,27);
        initState.set(7,8,24);

        // row 8
        initState.set(8,3,70);
        initState.set(8,7,25);

        // set coords of last num in the puzzle (1)
        initState.setLastPos(1, 0);
        initState.setDepth(1);
        space.push(initState);
    }


    public void initTestGame8() {
        space = new Space<>();

        State<Integer> initState = new State(9);
        // row 0
        initState.set(0,2,27);
        initState.set(0,6,71);
        initState.set(0,8,76);

        // row 1
        initState.set(1,0,31);
        initState.set(1,3,68);
        initState.set(1,4,25);
        initState.set(1,7,78);

        // row 2
        initState.set(2,8,81);

        // row 3
        initState.set(3,1,17);
        initState.set(3,4,66);

        // row 4
        initState.set(4,6,37);

        // row 5
        initState.set(5,4,8);
        initState.set(5,7,40);

        // row 6
        initState.set(6,0,1);
        initState.set(6,8,51);

        // row 7
        initState.set(7,1,12);
        initState.set(7,4,55);
        initState.set(7,5,42);
        initState.set(7,8,46);

        // row 8
        initState.set(8,2,58);
        initState.set(8,6,53);

        // set coords of last num in the puzzle (1)
        initState.setLastPos(6, 0);
        initState.setDepth(1);
        space.push(initState);
    }
}
