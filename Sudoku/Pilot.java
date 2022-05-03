import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Pilot {
    
    public static void main(String[] args) {
        //InputGenerator ip = new InputGenerator(36, 100);
        
        //for (int k = 0; k < 10; k++) {
        int noPuzzles = 100;
        int puzzleSize = 16;
        double sumTime = 0.00;
        //InputGenerator newPuzzles = new InputGenerator(puzzleSize, noPuzzles);
        //InputReader test = new InputReader(newPuzzles.getStringPuzzles());
        InputReader test = new InputReader(noPuzzles);
        boolean withSolutions = true;
        //InputReader test = new InputReader(noPuzzles);
        int size = test.getPuzzles()[0].getN();
        ArrayList<Puzzle> outputPuzzles = new ArrayList<Puzzle>();
        for (int i = 0; i < noPuzzles; i++) {

            Puzzle p = test.getPuzzles()[i];
            /*
            p.printPuzzle();
            Naive solveN = new Naive(p);
            Instant start = Instant.now();
            boolean solved = solveN.Solved();
            Instant end = Instant.now();
            double time = Duration.between(start, end).toMillis();
            sumTime = sumTime + time;  
            solveN.printPuzzle();
            System.out.println(solved + ": In " + time + " milliseconds, having " + p.getClues() + " clues.");
            */
            //p.printPuzzle();
            Instant start = Instant.now();
            CNFEncoder cnf = new CNFEncoder(p);
            SATSolver sat = new SATSolver(puzzleSize*puzzleSize*puzzleSize, cnf.getClauses());
            Instant end = Instant.now();
            double time = Duration.between(start, end).toMillis();
            System.out.println(sat.getResult());
            System.out.println("SAT solved in: " + time);
            Instant start2 = Instant.now();
            DancingLinks solveDL = new DancingLinks(p);
            boolean solved2 = solveDL.Solved();
            //Puzzle[] solvedPuzzles = solveDL.getSolvedPuzzles();
            //outputPuzzles.add(solvedPuzzles);
            Puzzle s = solveDL.getSolvedPuzzles()[0];
            Instant end2 = Instant.now();
            double time2 = Duration.between(start2, end2).toMillis();
            System.out.println("DLX solved in: " + time2);
            //sumTime = sumTime + time2;
            //solveDL.printPuzzle();
            //System.out.println(solved2 + ": In " + time2 + " milliseconds, having " + p.getClues() + " clues.");
            /*
            int repeat = 0;
            Puzzle bestPuzzle = s;
            while (repeat < 1) {
                SudokuReverser sr = new SudokuReverser(s);
                Puzzle newP = sr.getBestOption();
                //newP.printPuzzle();
                if (newP.getClues() < bestPuzzle.getClues()) {
                    bestPuzzle = newP;
                }
                repeat++;
            }
            outputPuzzles.add(bestPuzzle);
            outputPuzzles.add(s);
            //bestPuzzle.printPuzzle();
            Instant end2 = Instant.now();
            double time2 = Duration.between(start2, end2).toSeconds();
            sumTime = sumTime + time2;
            System.out.println("Run: " + i + " Clues found: " + bestPuzzle.getClues() + " In: " + time2 + " seconds");
            */
        }
        /*
        OutputGenerator op = new OutputGenerator(outputPuzzles, size, withSolutions);
        op.writeCsv();
        System.out.println("Run " + k + "/40 done in " + sumTime + " seconds.");
        System.out.println("Expected completion: " + (((40 - k) * sumTime) / 60) + " minutes");
        */
        //}
        
    }
}
