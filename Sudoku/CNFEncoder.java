import java.util.ArrayList;

public class CNFEncoder {
    
    private int nsize;
    private int bsize;
    private ArrayList<int[]> clauses;

    public CNFEncoder() {}

    public boolean Solved(Puzzle p) {
        clauses = encode(p);
        int size = p.getN();
        int maxvar = size*size*size;
        SATSolver sat = new SATSolver(maxvar, clauses);
        if (sat.getResult()) {return true;}
        return false;
    }

    public ArrayList<int[]> encode(Puzzle p) {

        nsize = p.getN();
        bsize = (int)Math.sqrt(nsize);

        for (int i = 0; i < p.getPuzzle().length; i++) {
            int[] leastoneclause = new int[nsize];
            for (int v = 1; v <= nsize; v++) {
                leastoneclause[v-1] = getVN(i, v);
            }
            clauses.add(leastoneclause);

            for (int v = 1; v <= nsize; v++) {
                for (int w = v+1; w <= nsize; w++) {
                    clauses.add(new int[]{-getVN(i,v), -getVN(i,w)});
                }
            }

            if (p.getPuzzle()[i] != 0) {
                clauses.add(new int[]{getVN(i, p.getPuzzle()[i])});
            }
        }
        
        for (int v = 1; v <= nsize; v++) {
            for (int r = 0; r < nsize; r++) {
                int[] rowclause = new int[nsize];
                for (int w = 0; w < nsize; w++) {
                    rowclause[w] = getVN(r*nsize+w, v);
                }
                clauses.add(rowclause);
            }
            for (int c = 0; c < nsize; c++) {
                int[] columnclause = new int[nsize];
                for (int w = 0; w < nsize; w++) {
                    columnclause[w] = getVN(c+(w*nsize),v);
                }
                clauses.add(columnclause);
            }
            for (int br = 0; br < bsize; br++) {
                for (int bc = 0; bc < bsize; bc++) {
                    int[] boxclause = new int[nsize];
                    int w = 0;
                    for (int r = 0; r < bsize; r++) {
                        for (int c = 0; c < bsize; c++) {
                            boxclause[w] = getVN((br*bsize*nsize)+(bc*bsize)+(r*nsize)+c, v);
                            w++;
                        }
                    }
                    clauses.add(boxclause);
                }
            }
        }
        return clauses;
    }

    private int getVN(int arrayIndex, int value) {
        return ((arrayIndex*nsize) + (value-1)+1); 
    }

    public ArrayList<int[]> getClauses() {
        return clauses;
    }
}
