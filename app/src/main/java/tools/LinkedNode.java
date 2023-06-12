package tools;

/**
 * The LinkedNode class represents a node in a linked list. It is used to store the segments in the
 * vertical and horizontal priority search trees.
 */
public class LinkedNode {
    /**
     * The segment represented by this node.
     */
    private final Double[] segment;
    /**
     * The next node in the list.
     */
    private LinkedNode next;
    /**
     * The previous node in the list.
     */
    private LinkedNode prev;

    /**
     * Constructs a new LinkedNode object.
     *
     * @param element the segment represented by this node
     */
    LinkedNode(LinkedNode prev, Double[] element) {
        this.segment = element;
        this.prev = prev;
    }

    /**
     * Gets the segment represented by this node.
     *
     * @return the segment represented by this node
     */
    public Double[] getItem() {
        return segment;
    }

    /**
     * Gets the next node in the list.
     *
     * @return the next node in the list
     */
    public LinkedNode getNext() {
        return next;
    }

    /**
     * Sets the next node in the list to the specified value.
     *
     * @param next the new next node in the list
     */
    public void setNext(LinkedNode next) {
        this.next = next;
    }

    /**
     * Gets the previous node in the list.
     *
     * @return the previous node in the list
     */
    public LinkedNode getPrev() {
        return prev;
    }

    /**
     * Sets the previous node in the list to the specified value.
     *
     * @param prev the new previous node in the list
     */
    public void setPrev(LinkedNode prev) {
        this.prev = prev;
    }
}
