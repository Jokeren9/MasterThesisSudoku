import java.util.Arrays;
import java.util.List;

public class DancingLinks {
    
    private int[] puzzle;
    private Puzzle[] solvedPuzzles;
    private int psize;
    private int bsize;

    public DancingLinks() {}

    public boolean Solved(Puzzle p) {
        puzzle = p.getPuzzle();
        psize = p.getN();
        bsize = (int)Math.sqrt(psize);
        int[][] coverMatrix = coverMatrix(puzzle);
        DLX dlx = new DLX(coverMatrix);
        solvedPuzzles = new Puzzle[dlx.getSolutions().size()];
        int i = 0;
        for (List<DancingNode> solution : dlx.getSolutions()) {
            Puzzle sol = new Puzzle(dlxToArray(solution), psize * psize);
            solvedPuzzles[i] = sol;
            puzzle = sol.getPuzzle();
            i++;
        }
        if (i > 0) {
            return true;
        }
        return false;
    }

    private int[][] coverMatrix(int[] puzzle) {
        int[][] coverMatrix = emptyCoverMatrix();

        for (int i = 0; i < puzzle.length; i++) {
            int cellValue = puzzle[i];
            if (cellValue != 0) {
                for (int number = 1; number <= psize; number++) {
                    if (number != cellValue) {
                        Arrays.fill(coverMatrix[indexCoverMatrix(i / psize, i % psize, number)], 0);
                    }
                }
            }
        }

        return coverMatrix;
    }

    private int[][] emptyCoverMatrix() {
        int[][] coverMatrix = new int[psize*psize*psize][psize*psize*4];
        int header = 0;

        header = cellConstraints(coverMatrix, header);
        header = rowConstraints(coverMatrix, header);
        header = columnConstraints(coverMatrix, header);
        boxConstraints(coverMatrix, header);

        return coverMatrix;
    }

    private int indexCoverMatrix(int row, int column, int number) {
        return (row*psize*psize + (column*psize) + (number-1));
    }

    private int cellConstraints(int[][] matrix, int header) {
        for (int row = 0; row < psize; row++) {
            for (int column = 0; column < psize; column++, header++) {
                for (int number = 1; number <= psize; number++) {
                    int index = indexCoverMatrix(row, column, number);
                    matrix[index][header] = 1;
                }
            }
        }

        return header;
    }

    private int rowConstraints(int[][] matrix, int header) {
        for (int row = 0; row < psize; row++) {
            for (int number = 1; number <= psize; number++, header++) {
                for (int column = 0; column < psize; column++) {
                    int index = indexCoverMatrix(row, column, number);
                    matrix[index][header] = 1;
                }
            }
        }
        
        return header;
    }

    private int columnConstraints(int[][] matrix, int header) {
        for (int column = 0; column < psize; column++) {
            for (int number = 1; number <= psize; number++, header++) {
                for (int row = 0; row < psize; row++) {
                    int index = indexCoverMatrix(row, column, number);
                    matrix[index][header] = 1;
                }
            }
        }
        
        return header;
    }

    private int boxConstraints(int[][] matrix, int header) {
        for (int row = 0; row < psize; row += bsize) {
            for (int column = 0; column < psize; column += bsize) {
                for (int number = 1; number <= psize; number++, header++) {
                    for (int rowBox = 0; rowBox < bsize; rowBox++) {
                        for (int columnBox = 0; columnBox < bsize; columnBox++) {
                            int index = indexCoverMatrix(row + rowBox, column + columnBox, number);
                            matrix[index][header] = 1;
                        }
                    }
                }
            }
        }

        return header;
    }

    private int[] dlxToArray(List<DancingNode> solution) {
        int[] newpuzzle = new int[psize * psize];
        for (DancingNode node : solution) {
            DancingNode answerNode = node;
            int min = Integer.parseInt(answerNode.cNode.name);

            for (DancingNode tmpNode = node.right; tmpNode != node; tmpNode = tmpNode.right) {
                int value = Integer.parseInt(tmpNode.cNode.name);

                if (value < min) {
                    min = value;
                    answerNode = tmpNode;
                }
            }

            int index = Integer.parseInt(answerNode.cNode.name);
            int cNumber = Integer.parseInt(answerNode.right.cNode.name);

            int number = (cNumber % psize) + 1;

            newpuzzle[index] = number;
        }
        return newpuzzle;
    }

    public void printPuzzle() {
        System.out.println("");
        System.out.print(" ");
        for (int i = 0; i < (psize*3)+(psize-1); i++) {
            System.out.print("-");
        }
        System.out.print(" ");
        System.out.println("");
        for (int i = 0; i < psize; i++) {
            System.out.print("| ");
            for (int j = 0; j < psize; j++) {
                System.out.print(puzzle[(j+i*psize)] + " | ");
            }
            System.out.println("");
            System.out.print(" ");
            for (int k = 0; k < (psize*3)+(psize-1); k++) {
                System.out.print("-");
            }
            System.out.print(" ");
            System.out.println("");
        }
    }

    public Puzzle[] getSolvedPuzzles() {
        return solvedPuzzles;
    }
}
