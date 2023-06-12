package tree;

import tools.LinkedNode;
import tools.SegmentsList;

import java.util.Objects;

/**
 * A priority search tree implementation for line segments.
 * Uses a recursive construction algorithm to build a priority search tree
 * with each node containing a line segment and its median y-coordinate.
 */
public class PrioritySearchTree {

    private final Node head;

    /**
     * Constructs a PrioritySearchTree object from a list of line segments.
     *
     * @param segments A List of Double arrays representing line segments.
     *                 Each Double array contains two values representing the start and end points of a line segment.
     */
    public PrioritySearchTree(SegmentsList segments) {
        head = createTree(segments, segments.getInitialSize() - 1);
    }

    /**
     * Recursive algorithm to create a balanced binary search tree from a list of line segments.
     * Chooses the leftmost segment in the list as the root node and constructs subtrees from the remaining segments.
     *
     * @param segments A List of Double arrays representing line segments.
     * @param end      The ending index of the current list of segments to be processed.
     * @return The root node of the newly constructed binary search tree.
     */
    private Node createTree(SegmentsList segments, int end) {
        Node target = new Node();
        int index = (end / 2);
        LinkedNode[] usefulNodes = getUsefulNodes(segments, end, index + end%2);
        target.setSegment(usefulNodes[0].getItem());
        segments.remove(usefulNodes[0]);
        if (end == 1) {
            target.setLeftChild(createTree(segments, 0));
        } else if (end > 1) {
            double firstY = usefulNodes[1].getItem()[1];
            double secondY = usefulNodes[1].getPrev().getItem()[1];
            target.setMedian((firstY + secondY) / 2);
            if (Objects.equals(firstY, secondY)) {
                target.setAreEdgesEqual(true);
            }
            target.setLeftChild(createTree(segments, index - 1 + end%2));
            target.setRightChild(createTree(segments, index - 1));
        }
        return target;
    }

    /**
     * Returns the leftmost segment in the list and the segment at the given index.
     *
     * @param segments A List of Double arrays representing line segments.
     * @param end      The ending index of the current list of segments to be processed.
     * @param index    The index of the segment to be returned.
     * @return An array containing the leftmost segment and the segment at the given index.
     */
    private LinkedNode[] getUsefulNodes(SegmentsList segments, int end, int index) {
        int leftmostSegmentIndex = 0;
        LinkedNode node = segments.get(0);
        LinkedNode leftmostNode = node;
        LinkedNode indexNode = node;
        for (int i = 1; i < end + 1; i++) {
            LinkedNode nextNode = node.getNext();
            if (nextNode.getItem()[0] < leftmostNode.getItem()[0]) {
                leftmostSegmentIndex = i;
                leftmostNode = nextNode;
            }
            if (i == index) {
                indexNode = nextNode;
            }
            node = nextNode;
        }
        if (leftmostSegmentIndex <= index) {
            indexNode = indexNode.getNext();
        }
        return new LinkedNode[]{leftmostNode, indexNode};
    }

    /**
     * Returns the root node of the binary search tree.
     *
     * @return The root node of the binary search tree.
     */
    public Node getHead() {
        return head;
    }
}
