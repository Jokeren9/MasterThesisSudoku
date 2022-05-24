import java.util.ArrayList;
import java.util.Collections;

public class SudokuReverser {
    
    private int[] puzzle;
    private int[] bestPuzzle;
    private int clues;
    private int bestClues;
    private int size;
    private ArrayList<Integer> indexList = new ArrayList<Integer>();
    private int solutions;
    private final int totalTries = 10000;
    private int failedTries = 0;

    public SudokuReverser(Puzzle p) {
        puzzle = p.getPuzzle();
        clues = p.getClues();
        bestClues = clues;
        size = p.getN();
        solutions = 1;

        for (int i = 0; i < size*size; i++) {
            indexList.add(i);
        }
        
        Collections.shuffle(indexList);
        //reverseRecursively(size*size);
        reverseIteratively(size*size);
    }

    private void reverseIteratively(int size) {
        for (int index = indexList.size()-1; index >= 0; index--) {
            int removedIndex = indexList.remove(index);

            int removedNumber = puzzle[removedIndex];
            puzzle[removedIndex] = 0;
            clues--;

            DancingLinks dl = new DancingLinks();
            dl.Solved(new Puzzle(puzzle, clues));
            solutions = dl.getSolvedPuzzles().length;

            if (solutions != 1) {
                puzzle[removedIndex] = removedNumber;
                clues++;
                bestPuzzle = puzzle;
                bestClues = clues;
                break;      
            }
        }
    }

    private void reverseRecursively(int size) {
        if (clues < bestClues && solutions == 1) {
            bestPuzzle = puzzle;
            bestClues = clues;
        }
        
        for (int i = 0; i < size; i++) {
                   
            int index = indexList.get(indexList.size()-1);
            indexList.remove(indexList.size()-1);

            int removedNumber = puzzle[index];
            puzzle[index] = 0;
            clues--;

            DancingLinks dl = new DancingLinks();
            dl.Solved(new Puzzle(puzzle, clues));
            solutions = dl.getSolvedPuzzles().length;

            if (solutions == 1 && failedTries < totalTries) {
                reverseRecursively(size);
            }
            failedTries++;
            puzzle[index] = removedNumber;
            indexList.add(0, index);
            clues++;
        }
    }

    public Puzzle getBestOption() {
        return new Puzzle(bestPuzzle, bestClues);
    }
}
