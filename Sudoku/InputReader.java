import java.io.*;

public class InputReader {
    
    private int noPuzzles;
    private Puzzle[] puzzles;
    private SudokuMap sudokuMap = new SudokuMap();

    public InputReader(int n) {
        noPuzzles = n;
        puzzles = new Puzzle[n];
        
        try {
            String filePath = new File("").getAbsolutePath();
            File file = new File(filePath + "\\generated_data\\19-03-2022 07.14\\1000 16x16 with solutions.csv");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int noRead = 0;
            while (noRead < noPuzzles) {
                String read = reader.readLine();
                if (!read.startsWith("#")) {
                    String newRead = read.split(",")[0];
                    puzzles[noRead] = new Puzzle(newRead, sudokuMap);
                    noRead++;
                }
            }
            reader.close();
        } catch (Exception e) {
           
        }
    }

    public InputReader(String[] sPuzzles) {
        SudokuMap sudokuMap = new SudokuMap();
        noPuzzles = sPuzzles.length;
        puzzles = new Puzzle[noPuzzles];

        for (int i = 0; i < sPuzzles.length; i++) {
            puzzles[i] = new Puzzle(sPuzzles[i], sudokuMap);
        }
    }

    public Puzzle[] getPuzzles() {
        return puzzles;
    }
}
