import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DLX {
    
    private ColumnNode header;
    private List<DancingNode> answer;
    private List<DancingNode> solution;
    private List<List<DancingNode>> solutions;
    private int counter = 0;

    public DLX(int[][] coverMatrix) {
        header = DLXList(coverMatrix);
        answer = new LinkedList<DancingNode>();
        solutions = new LinkedList<>();
        traverse(0);
    }

    private ColumnNode DLXList(int[][] matrix) {
        int noColumns = matrix[0].length;
        ColumnNode headerNode = new ColumnNode("header");
        List<ColumnNode> columnNodes = new ArrayList<>();

        for (int i = 0; i < noColumns; i++) {
            ColumnNode columnNode = new ColumnNode(i + "");
            columnNodes.add(columnNode);
            headerNode = (ColumnNode) headerNode.linkRight(columnNode);
        }

        headerNode = headerNode.right.cNode;

        for (int[] array : matrix) {
            DancingNode prev = null;

            for (int j = 0; j < noColumns; j++) {
                if (array[j] == 1) {
                    ColumnNode columnNode = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(columnNode);

                    if (prev == null) {
                        prev = newNode;
                    }

                    columnNode.top.linkDown(newNode);
                    prev = prev.linkRight(newNode);
                    columnNode.size++;
                }
            }
        }

        headerNode.size = noColumns;

        return headerNode;
    }

    private void traverse(int k) {
        if(header.right == header) {
            solution = new LinkedList<>(answer);
            solutions.add(solution);
            counter++;
            /*
            if (counter > 0) {
                return;
            }
            */
        } else {
            ColumnNode cNode = minHeader();
            cNode.cover();

            for (DancingNode yNode = cNode.bottom; yNode != cNode; yNode = yNode.bottom) {
                answer.add(yNode);

                for (DancingNode xNode = yNode.right; xNode != yNode; xNode = xNode.right) {
                    xNode.cNode.cover();
                }

                traverse(k + 1);

                
                if (counter > 1) {
                    return;
                }
                

                yNode = answer.remove(answer.size() - 1);
                cNode = yNode.cNode;

                for (DancingNode xNode = yNode.left; xNode != yNode; xNode = xNode.left) {
                    xNode.cNode.uncover();
                }
            }

            cNode.uncover();
        }
    }

    private ColumnNode minHeader() {
        ColumnNode cNode = null;
        int minValue = Integer.MAX_VALUE;

        for (ColumnNode node = (ColumnNode) header.right; node != header; node = (ColumnNode) node.right) {
            if (node.size < minValue) {
                minValue = node.size;
                cNode = node;
            }

            if (minValue == 1) {
                break;
            }
        }

        return cNode;
    }

    public List<DancingNode> getSolution() {
        return solution;
    }

    public List<List<DancingNode>> getSolutions() {
        return solutions;
    }
}
