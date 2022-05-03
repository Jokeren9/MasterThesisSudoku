import java.util.Map;
import java.util.TreeMap;

public class SudokuMap {
    
    public TreeMap<String, Integer> sudokuMap = new TreeMap<String, Integer>();

    public SudokuMap() {
        sudokuMap.put(".", 0);
        sudokuMap.put("1", 1);
        sudokuMap.put("2", 2);
        sudokuMap.put("3", 3);
        sudokuMap.put("4", 4);
        sudokuMap.put("5", 5);
        sudokuMap.put("6", 6);
        sudokuMap.put("7", 7);
        sudokuMap.put("8", 8);
        sudokuMap.put("9", 9);
        sudokuMap.put("A", 10);
        sudokuMap.put("B", 11);
        sudokuMap.put("C", 12);
        sudokuMap.put("D", 13);
        sudokuMap.put("E", 14);
        sudokuMap.put("F", 15);
        sudokuMap.put("G", 16);
        sudokuMap.put("H", 17);
        sudokuMap.put("I", 18);
        sudokuMap.put("J", 19);
        sudokuMap.put("K", 20);
        sudokuMap.put("L", 21);
        sudokuMap.put("M", 22);
        sudokuMap.put("N", 23);
        sudokuMap.put("O", 24);
        sudokuMap.put("P", 25);
        sudokuMap.put("Q", 26);
        sudokuMap.put("R", 27);
        sudokuMap.put("S", 28);
        sudokuMap.put("T", 29);
        sudokuMap.put("U", 30);
        sudokuMap.put("V", 31);
        sudokuMap.put("W", 32);
        sudokuMap.put("X", 33);
        sudokuMap.put("Y", 34);
        sudokuMap.put("Z", 35);
        sudokuMap.put("a", 36);
    }

    public String getKey(int value) {
        String key = "";
        for (Map.Entry<String, Integer> entry : sudokuMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

    public Integer getValue(String key) {
        Integer value = sudokuMap.get(key);

        return value;
    }
}
