import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class InputGenerator {
    private String path;
    private String[] puzzles;
    private int size;
    private int blocksize;
    private int target;

    public InputGenerator(int size, int target) {
        this.size = size;
        blocksize = (int)Math.sqrt(size);
        this.target = target;
        this.puzzles = new String[target];

        generateInput();
    }

    private void generateInput() {
        SudokuMap sudokuMap = new SudokuMap();
        ArrayList<String> letters = new ArrayList<String>();
        for (int i = 1; i <= size; i++) {
            letters.add(sudokuMap.getKey(i));
        }
        String blankblock = ".".repeat(blocksize);
        
        for (int j = 0; j < target; j++) {
            
            StringBuilder sb = new StringBuilder();          
            for (int k = 0; k < blocksize; k++) {
                int index = 0;
                Collections.shuffle(letters);
                for (int l = 0; l < blocksize; l++) {
                    sb.append(blankblock.repeat(k));
                    for (int m = index; m < index+blocksize; m++) {
                        sb.append(letters.get(m));
                    }
                    sb.append(blankblock.repeat(blocksize-k-1));
                    index = index + blocksize;
                }
            }
            puzzles[j] = sb.toString();
        }
    }

    public String[] getStringPuzzles() {
        return puzzles;
    }

    public void writeCsv() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH.mm");
        path = "generated_data/" + formatter.format(date);
        File file = new File(path);
        file.mkdir();

        File f = new File(path + "/" + size + "x" + size + "-" + target + " puzzles" + ".csv");
        try (PrintWriter pw = new PrintWriter(f);) {
            for (int i = 0; i < puzzles.length; i++) {
                String[] fields = new String[] {
                    puzzles[i]
                };
                pw.println(String.join(",",fields));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
