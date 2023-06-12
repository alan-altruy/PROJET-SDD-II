package tools;

import java.util.ArrayList;

/**
 * The SegmentsList class represents a linked list of Double arrays. It is used to store the segments
 * in the vertical and horizontal priority search trees.
 */
public class SegmentsList {
    /**
     * The initial size of the list.
     */
    private final int initialSize;
    /**
     * The first node in the list.
     */
    private LinkedNode head;

    /**
     * Constructs a new SegmentsList object.
     *
     * @param arrayList the list of segments
     */
    public SegmentsList(ArrayList<Double[]> arrayList) {
        head = createList(arrayList);
        initialSize = arrayList.size();
    }

    /**
     * Creates a linked list from the given ArrayList.
     *
     * @param arrayList the ArrayList to convert
     * @return the first node in the list
     */
    private LinkedNode createList(ArrayList<Double[]> arrayList) {
        LinkedNode firstNode = new LinkedNode(null, arrayList.get(0));
        LinkedNode prev = firstNode;
        for (int i = 1; i < arrayList.size(); i++) {
            LinkedNode node = new LinkedNode(prev, arrayList.get(i));
            prev.setNext(node);
            prev = node;
        }
        return firstNode;
    }

    /**
     * Gets the first node in the list.
     *
     * @return the first node in the list
     */
    public LinkedNode get(int index) {
        LinkedNode node = head;
        for (int i = 0; i < index; i++) {
            node = node.getNext();
        }
        return node;
    }

    /**
     * Removes the given node from the list.
     *
     * @param node the node to remove
     */
    public void remove(LinkedNode node) {
        LinkedNode next = node.getNext();
        LinkedNode prev = node.getPrev();

        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }
    }

    /**
     * Gets the initial size of the list.
     *
     * @return the initial size of the list
     */
    public int getInitialSize() {
        return initialSize;
    }
}
