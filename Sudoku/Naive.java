public class Naive {
    
    private int[] puzzle;
    private int n;

    public Naive() {}

    public boolean Solved(Puzzle p) {
        puzzle = p.getPuzzle();
        n = p.getN();
        if (Solve(puzzle)) {return true;};        
        return false;
    }

    public boolean Solve(int[] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            if (puzzle[i] == 0) {
                for (int j = 1; j <= n; j++) {
                    if (isValid(j, i)) {
                        puzzle[i] = j;
                        if (!Solve(puzzle)) {
                            puzzle[i] = 0;
                        } else { 
                            return true;
                        }     
                    }
                }
                return false;
            } else {
                int v = puzzle[i];
                puzzle[i] = 0;
                if (!isValid(v,i)) {
                    puzzle[i] = v;
                    return false;
                } else {
                    puzzle[i] = v;
                }
            }
        }
        return true;
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

    private boolean isValid(int number, int index) {
        if (InRow(number, index) || InColumn(number, index) || InBox(number, index)) {
            return false;
        }
        return true;
    }

    private boolean InRow(int number, int index) {
        int row = index / n;
        for (int i = row*n; i < (row+1)*n; i++) {
            if (puzzle[i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean InColumn(int number, int index) {
        int column = index % n;
        for (int i = column; i < puzzle.length; i = i+n) {
            if (puzzle[i] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean InBox(int number, int index) {
        int boxN = (int)Math.sqrt(n);
        int boxX = (index % n) / boxN;
        int boxY = (index / n) / boxN;
        for (int i = boxY*boxN; i < (boxY*boxN)+boxN; i++) {
            for (int j = (i*n)+(boxX*boxN) ; j < (i*n)+(boxX*boxN)+boxN; j++) {
                if (puzzle[j] == number) {
                    return true;
                }
            }
        }
        return false;
    }
}