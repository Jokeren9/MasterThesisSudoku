public class ColumnNode extends DancingNode {
    
    public int size;
    public String name;

    public ColumnNode(String name) {
        super();
        size = 0;
        this.name = name;
        cNode = this;
    }

    public void cover() {
        removeLeftRight();

        for (DancingNode yNode = bottom; yNode != this; yNode = yNode.bottom) {
            for (DancingNode xNode = yNode.right; xNode != yNode; xNode = xNode.right) {
                xNode.removeTopBottom();
                xNode.cNode.size--;
            }
        }
    }

    public void uncover() {
        for (DancingNode yNode = top; yNode != this; yNode = yNode.top) {
            for (DancingNode xNode = yNode.left; xNode != yNode; xNode = xNode.left) {
                xNode.cNode.size++;
                xNode.insertTopBottom();
            }
        }

        insertLeftRight();
    }
}
