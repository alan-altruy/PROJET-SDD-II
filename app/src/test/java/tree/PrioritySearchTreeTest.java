package tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.SegmentsList;

import java.util.ArrayList;

class PrioritySearchTreeTest {
    @Test
    public void createTreeTest() {
        ArrayList<Double[]> arrayList = createList();
        SegmentsList list = new SegmentsList(arrayList);
        PrioritySearchTree tree = new PrioritySearchTree(list);
        isItATree(tree.getHead());
    }

    private void isItATree(Node node){
        if (node.hasLeftChild()){
            if (node.hasRightChild()){
                Assertions.assertTrue(node.getSegment()[0] <= node.getRightChild().getSegment()[0]);
                Assertions.assertTrue(node.getMedian() <= node.getRightChild().getSegment()[1]);
                Assertions.assertTrue(node.getMedian() >= node.getLeftChild().getSegment()[1]);
                isItATree(node.getRightChild());
            }
            Assertions.assertTrue(node.getSegment()[0] <= node.getLeftChild().getSegment()[0]);
            isItATree(node.getLeftChild());
        }
    }

    private ArrayList<Double[]> createList(){
        ArrayList<Double[]> list = new ArrayList<>();
        Double[] segment1 = new Double[]{-454.0, -504.0, -266.0, -504.0};
        Double[] segment2 = new Double[]{-502.0, -477.0, 414.0, -477.0};
        Double[] segment3 = new Double[]{-948.0, -258.0, -910.0, -258.0};
        Double[] segment4 = new Double[]{-97.0, -16.0, 418.0, -16.0};
        Double[] segment5 = new Double[]{-894.0, 203.0, 167.0, 203.0};
        Double[] segment6 = new Double[]{-613.0, 574.0, -328.0, 574.0};
        Double[] segment7 = new Double[]{319.0, 576.0, 320.0, 576.0};
        Double[] segment8 = new Double[]{-289.0, 701.0, 483.0, 701.0};
        Double[] segment9 = new Double[]{517.0, 823.0, 791.0, 823.0};
        Double[] segment10 = new Double[]{-640.0, 873.0, 402.0, 873.0};
        Double[] segment11 = new Double[]{-594.0, 981.0, -364.0, 981.0};
        list.add(segment1);
        list.add(segment2);
        list.add(segment3);
        list.add(segment4);
        list.add(segment5);
        list.add(segment6);
        list.add(segment7);
        list.add(segment8);
        list.add(segment9);
        list.add(segment10);
        list.add(segment11);
        return list;
    }
}
