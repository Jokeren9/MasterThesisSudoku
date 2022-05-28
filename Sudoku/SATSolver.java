import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class SATSolver {

    private boolean satisfiable;
    
    public SATSolver(String file) {

        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);
        Reader reader = new DimacsReader(solver);
        
        PrintWriter out = new PrintWriter(System.out,true);

        try {
            IProblem problem = reader.parseInstance(file);
            if (problem.isSatisfiable()) {
                satisfiable = true;
                //System.out.println("Satisfiable");
                reader.decode(problem.model(),out);
            } else {
                satisfiable = false;
                //System.out.println("Unsatisfiable");
            }
        } catch (FileNotFoundException e) {
        } catch (ParseFormatException e) {
        } catch (IOException e) {
        } catch (ContradictionException e) {
        } catch (TimeoutException e) {
        }
    }

    public SATSolver(int maxvar, ArrayList<int[]> clauses) {
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);
        solver.newVar(maxvar);
        solver.setExpectedNumberOfClauses(clauses.size());
        
        try {
            for (int[] clause : clauses) {
                solver.addClause(new VecInt(clause));
            }

            IProblem problem = solver;
            if (problem.isSatisfiable()) {
                satisfiable = true;
                //System.out.println("Satisfiable");
            } else {
                satisfiable = false;
                //System.out.println("Unsatisfiable");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public boolean getResult() {
        return satisfiable;
    }
}
