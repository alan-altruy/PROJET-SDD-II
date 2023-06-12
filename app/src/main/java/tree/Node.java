package tree;

/**
 * This class represents a node in a priority search tree.
 */
public class Node {
    private Double[] segment;
    private double median;
    private boolean areEdgesEqual;
    private Node leftChild;
    private boolean hasLeftChild;
    private Node rightChild;
    private boolean hasRightChild;

    /**
     * Constructs a new instance of Node with default values.
     */
    public Node() {
    }

    /**
     * Returns the segment of this node.
     *
     * @return the segment of this node
     */
    public Double[] getSegment() {
        return segment;
    }

    /**
     * Sets the segment of this node to the specified value.
     *
     * @param segment the new segment of this node
     */
    public void setSegment(Double[] segment) {
        this.segment = segment;
    }

    /**
     * Returns the median of this node.
     *
     * @return the median of this node
     */
    public double getMedian() {
        return median;
    }

    /**
     * Sets the median of this node to the specified value.
     *
     * @param median the new median of this node
     */
    public void setMedian(double median) {
        this.median = median;
    }

    /**
     * Returns whether the edges of this node are equal.
     *
     * @return true if the edges of this node are equal, false otherwise
     */
    public boolean areEdgesNotEqual() {
        return !areEdgesEqual;
    }

    /**
     * Sets whether the edges of this node are equal.
     *
     * @param areEdgesEqual true if the edges of this node are equal, false otherwise
     */
    public void setAreEdgesEqual(boolean areEdgesEqual) {
        this.areEdgesEqual = areEdgesEqual;
    }

    /**
     * Returns the left child of this node.
     *
     * @return the left child of this node
     */
    public Node getLeftChild() {
        return leftChild;
    }

    /**
     * Sets the left child of this node to the specified node.
     *
     * @param leftChild the new left child of this node
     */
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        hasLeftChild = true;
    }

    /**
     * Returns the right child of this node.
     *
     * @return the right child of this node
     */
    public Node getRightChild() {
        return rightChild;
    }

    /**
     * Sets the right child of this node to the specified node.
     *
     * @param rightChild the new right child of this node
     */
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        hasRightChild = true;
    }

    /**
     * Returns whether this node has a left child.
     *
     * @return true if this node has a left child, false otherwise
     */
    public boolean hasLeftChild() {
        return hasLeftChild;
    }

    /**
     * Returns whether this node has a right child.
     *
     * @return true if this node has a right child, false otherwise
     */
    public boolean hasRightChild() {
        return hasRightChild;
    }
}
