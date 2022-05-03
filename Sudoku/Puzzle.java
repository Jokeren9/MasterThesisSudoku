import java.util.Arrays;

public class Puzzle {
    
    private int[] puzzle;
    private int clues = 0;
    private int n;

    public Puzzle(String line, SudokuMap sudokoMap) {
        char[] input = line.toCharArray();
        int size = input.length;
        puzzle = new int[size];
        n = (int)Math.sqrt(size);
        int position = 0;
        for (char Char : input) {
            puzzle[position] = sudokoMap.getValue(String.valueOf(Char));
            if (sudokoMap.getValue(String.valueOf(Char)) != 0) {
                clues++;
            }
            position++;
            /*
            if (Char == 48 || Char == 46) {
                puzzle[position] = 0;
            } else if (Char < 65) {
                puzzle[position] = Char-48;
                clues++;
            } else {
                puzzle[position] = Char-55;
                clues++;
            }
            position++;
            */
        }
    }

    public Puzzle(int[] puzzle, int clues) {
        this.puzzle = puzzle;
        n = (int)Math.sqrt(puzzle.length);
        this.clues = clues;
    }

    public int[] getPuzzle() {
        return Arrays.copyOf(puzzle, puzzle.length);
    }

    public int getClues() {
        return clues;
    }

    public int getN() {
        return n;
    }

    public void printPuzzle() {
        System.out.println("");
        System.out.print(" ");
        for (int i = 0; i < (n*3)+(n-1); i++) {
            System.out.print("-");
        }
        System.out.print(" ");
        System.out.println("");
        for (int i = 0; i < n; i++) {
            System.out.print("| ");
            for (int j = 0; j < n; j++) {
                System.out.print(puzzle[(j+i*n)] + " | ");
            }
            System.out.println("");
            
            System.out.print(" ");
            for (int k = 0; k < (n*3)+(n-1); k++) {
                System.out.print("-");
            }
            System.out.print(" ");
            System.out.println("");
        }
    }
}
