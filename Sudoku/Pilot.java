import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class Pilot {

    private static String path;
    
    public static void main(String[] args) {
        
        for (int n = 6; n <= 6; n++) {
        int noPuzzles = 50;
        int puzzleSize = n*n;

        switch (n) {
            case 3:
                path = "\\generated_data\\18-03-2022 13.40\\1000 9x9 with solutions.csv";
                break;
            case 4:
                path = "\\generated_data\\19-03-2022 07.14\\1000 16x16 with solutions.csv";
                break;

            case 5:
                path = "\\generated_data\\22-03-2022 09.30\\750 25x25 with solutions.csv";
                break;

            case 6:
                path = "\\generated_data\\23-03-2022 10.47\\80 36x36 with solutions.csv";
                break;
            default:
                break;
        }

        InputReader input = new InputReader(noPuzzles, path);
        noPuzzles = 100;
        runBenchmark(input, noPuzzles, n);
        }
    }

    public static double measure(Runnable f) {
        Instant start = Instant.now();
        f.run();
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos()/1e9;
    }

    public static double[][] benchmark(Consumer<Puzzle> f, Puzzle[] args, int N, boolean run) {
        int m = args.length;
        double[][] M = new double[m][N];
        for (int i = 0; i < m; i++) {
            if (run) {
                for (int j = 0; j < N; j++) {
                    Puzzle arg = new Puzzle(args[i].getPuzzle(), args[i].getN());
                    M[i][j] = measure(() -> f.accept(arg));
                    System.out.println("Puzzle " + i + " Done in " + M[i][j] + " seconds");
                }
            }
        }
        double[][] R = new double[m][2];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < N; j++) {
                R[i][0] += M[i][j];
            }
            R[i][0] /= N;
            for (int j = 0; j < N; j++) {
                double x = M[i][j] - R[i][0];
                R[i][1] += x*x;
            }
            R[i][1] = Math.sqrt(R[i][1] / (N-1));
        }
        return R;
    }

    public static void runBenchmark(InputReader input, int noPuzzles, int n) {
        int N = 5;
        int[] ns = new int[noPuzzles];
        Puzzle[] args1 = input.getPuzzles();
        for (int i = 0; i < noPuzzles; i++) {
            ns[i] = input.getPuzzles()[i].getClues();
        }
        boolean run = true;
        if (n > 3) {run = false;}

        double[][] resNaive, resSAT, resDLX;
        resNaive = benchmark(new Naive()::Solved, args1, N, run);
        run = true;
        if (n > 4) {run = false;}
        resSAT = benchmark(new CNFEncoder()::Solved, args1, N, run);
        run = true;
        resDLX = benchmark(new DancingLinks()::Solved, args1, N, run);
        writeCsv(ns, resNaive, "experiment_data/Naive_n=" + n + ".csv");
        writeCsv(ns, resSAT, "experiment_data/SAT_n=" + n + ".csv");
        writeCsv(ns, resDLX, "experiment_data/DLX_n=" + n + ".csv");
        //writeLatexTabular(ns, resClassic, path + "/classic_tabular_" + comment + ".tex");
    }

    public static void writeCsv(int[] ns, double[][] res, String filename) {
        File f = new File(filename);
        try (PrintWriter pw = new PrintWriter(f);) {
            for (int i = 0; i < ns.length; i++) {
                String[] fields = new String[] {
                    Integer.toString(ns[i]),
                    String.format("%.17f", res[i][0]),
                    String.format("%.17f", res[i][1])
                };
                pw.println(String.join(",",fields));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
