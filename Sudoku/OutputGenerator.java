import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OutputGenerator {
    
    private int noPuzzles;
    private int size;
    private int incrementer;
    private String path;
    private String[] solvedPuzzles;

    public OutputGenerator(ArrayList<Puzzle> puzzlesArr, int size, boolean withSolutions) {
        
        if (withSolutions) {
            int arraySize = (puzzlesArr.size()) / 2;
            solvedPuzzles = new String[arraySize];
        } else {
            solvedPuzzles = new String[puzzlesArr.size()];
        }
        
        this.size = size;

        

        incrementer = 0;
        SudokuMap sudokuMap = new SudokuMap();

        generateOutput(puzzlesArr, sudokuMap, withSolutions);

    }

    private void generateOutput(ArrayList<Puzzle> puzzles, SudokuMap sudokuMap, boolean flag) {
        
        for (int j = 0; j < puzzles.size(); j++) {
            int[] p = puzzles.get(j).getPuzzle();
            
            int clues = puzzles.get(j).getClues();
            StringBuilder sb = new StringBuilder();
            
            if (flag) {
                if (j % 2 == 0) {
                    for (int k = 0; k < size*size; k++) {
                        sb.append(sudokuMap.getKey(p[k]));
                    }
                    sb.append(",");
                    sb.append(clues);
                
                    sb.append(",");
                    int[] p2 = puzzles.get(j+1).getPuzzle();
                    for (int k = 0; k < size*size; k++) {
                        sb.append(sudokuMap.getKey(p2[k]));
                    }
                
                    solvedPuzzles[incrementer] = sb.toString();
                    incrementer++;
                }
            } else {
                for (int k = 0; k < size*size; k++) {
                    sb.append(sudokuMap.getKey(p[k]));
                }
                sb.append(",");
                sb.append(clues);
                solvedPuzzles[incrementer] = sb.toString();
                incrementer++;
            }
        }
    } 

    public void writeCsv() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH.mm");
        path = "generated_data/" + formatter.format(date);
        File file = new File(path);
        file.mkdir();

        File f = new File(path + "/" + size + "x" + size + "-" + noPuzzles + " solutions" + ".csv");
        try (PrintWriter pw = new PrintWriter(f);) {
            for (int i = 0; i < solvedPuzzles.length; i++) {
                String[] fields = new String[] {
                    solvedPuzzles[i]
                };
                pw.println(String.join(",",fields));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
