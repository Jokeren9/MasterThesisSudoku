import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class Pilot {

    private static String path;
    
    public static void main(String[] args) {
        setupCountBenchmark();
    }

    public static void setupCountBenchmark() {
        Puzzle[] args = new Puzzle[4];
        for (int n = 3; n <= 6; n++) {
            int noPuzzles = 80;
    
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
            Puzzle maxpuzzle = new Puzzle(new int[1], 0);
            int maxclues = 0;
            //int minclues = 2000;
            for (Puzzle p : input.getPuzzles()) {
                if (p.getClues() > maxclues) {
                    maxclues = p.getClues();
                    maxpuzzle = p;
                }
            }
            args[n-3] = maxpuzzle;
        }
        //writePuzzleLatex(args, "Puzzle_");
        runCountBenchmark(args, 30);
    }

    public static void setupTimeBenchmark() {
        for (int n = 3; n <= 6; n++) {
            int noPuzzles = 80;
    
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
            runTimeBenchmark(input, noPuzzles, n);
            }
    }

    public static double measure(Runnable f) {
        Instant start = Instant.now();
        f.run();
        Instant end = Instant.now();
        return Duration.between(start, end).toNanos()/1e9;
    }

    public static double[][] benchmarkCount(Consumer<Puzzle> f, Puzzle[] args, double timeout, int N) {
        int m = args.length;
        double[][] M = new double[m][N];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < N; j++) {
                int count = 0;
                double timer = timeout;
                while(timer > 0) {
                Puzzle arg = new Puzzle(args[i].getPuzzle(), args[i].getClues());
                timer -= measure(() -> f.accept(arg));
                if (timer > 0) {
                    M[i][j]++;
                    count++;
                }
                }
                System.out.println(count);
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

    public static double[][] benchmarkTime(Consumer<Puzzle> f, Puzzle[] args, int N, boolean run) {
        int m = args.length;
        double[][] M = new double[m][N];
        for (int i = 0; i < m; i++) {
            if (run) {
                for (int j = 0; j < N; j++) {
                    Puzzle arg = new Puzzle(args[i].getPuzzle(), args[i].getClues());
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

    public static void runCountBenchmark(Puzzle[] args, double timeout) {
        int[] ns = new int[]{3,4,5,6};
        int N = 5;
        double[][] resNaive, resSAT, resDLX;
        
        resNaive = benchmarkCount(new Naive()::Solved, args, timeout, N);
        writeCsv(ns, resNaive, "experiment_data/Naive_Count.csv");
        resSAT = benchmarkCount(new CNFEncoder()::Solved, args, timeout, N);
        writeCsv(ns, resSAT, "experiment_data/SAT_Count.csv");
        resDLX = benchmarkCount(new DancingLinks()::Solved, args, timeout, N);
        writeCsv(ns, resDLX, "experiment_data/DLX_Count.csv");
    }

    public static void runTimeBenchmark(InputReader input, int noPuzzles, int n) {
        int N = 5;
        int[] ns = new int[noPuzzles];
        Puzzle[] args1 = input.getPuzzles();
        for (int i = 0; i < noPuzzles; i++) {
            ns[i] = input.getPuzzles()[i].getClues();
        }

        boolean run = true;
        

        double[][] resNaive, resSAT, resDLX;
        if (n > 3) {run = false;} //Stops naive after n = 3
        resNaive = benchmarkTime(new Naive()::Solved, args1, N, run);
        writeCsv(ns, resNaive, "experiment_data/Naive_n=" + n + ".csv");

        run = true;
        if (n > 5) {run = false;} //Stops SAT after n = 5
        resSAT = benchmarkTime(new CNFEncoder()::Solved, args1, N, run);
        writeCsv(ns, resSAT, "experiment_data/SAT_n=" + n + ".csv");
        
        run = true;
        resDLX = benchmarkTime(new DancingLinks()::Solved, args1, N, run);  
        writeCsv(ns, resDLX, "experiment_data/DLX_n=" + n + ".csv");

    }

    public static void writePuzzleLatex(Puzzle[] args, String filename) {
        for (int i = 0; i < args.length; i++) {
        File f = new File(filename + i + ".tex");
        int[] array = args[i].getPuzzle();
        try (PrintWriter pw = new PrintWriter(f);){
            pw.println("\\begin{tabular}{|*{" + args[i].getN() + "}}");
            pw.println("\\\\hline");
            int index = 0;
            for (int j = 0; j < args[i].getN(); j++) {
                String[] fields = new String[args[i].getN()]; {
                    for (int k = 0; k < args[i].getN(); k++) {
                        if (array[index] != 0) {
                            fields[k] = Integer.toString(array[index]);
                        } else {
                            fields[k] = "";
                        }
                        
                        index++;
                    }
                };
                pw.println(String.join(" & ", fields) + "\\\\hline");
            }
            pw.println("\\end{tabular}");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        }
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
