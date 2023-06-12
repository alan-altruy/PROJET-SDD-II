package windowing;

import tree.Node;
import tree.PrioritySearchTree;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class implements the windowing algorithm for 2D range searching in a priority search tree.
 * It launches the algorithm with given bounding boxes, maximum bounding boxes and priority search trees.
 */
public class WindowingAlgorithm {
    /**
     * ArrayList containing reported segments after the algorithm is executed.
     */
    ArrayList<Double[]> reportedSegments;

    /**
     * Constructs a new WindowingAlgorithm object.
     */
    public WindowingAlgorithm() {
        reportedSegments = new ArrayList<>();
    }

    /**
     * Launches the windowing algorithm on the given priority search trees, bounding boxes and maximum bounding boxes.
     *
     * @param vTree     vertical priority search tree
     * @param hTree     horizontal priority search tree
     * @param bounds    bounding box array consisting of bottom, top, left and right bounds
     * @param maxBounds maximum bounding box array consisting of bottom, top, left and right bounds
     * @return an ArrayList of Double arrays containing reported segments
     */
    public ArrayList<Double[]> launchWindowing(PrioritySearchTree vTree, PrioritySearchTree hTree, Double[] bounds,
                                               Double[] maxBounds) {
        Double[] hMaxBounds = new Double[]{maxBounds[0], maxBounds[1]};
        Double[] vMaxBounds = new Double[]{maxBounds[2], maxBounds[3]};
        Double[] vBounds = new Double[]{bounds[2], bounds[3], bounds[0], bounds[1]};
        windowing(hTree, bounds, hMaxBounds, false);
        windowing(vTree, vBounds, vMaxBounds, true);
        return reportedSegments;
    }

    /**
     * Calls the appropriate search function based on the bounding box's position relative to the maximum bounding box.
     *
     * @param tree       the priority search tree to be searched
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param maxBounds  maximum bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void windowing(PrioritySearchTree tree, Double[] bounds, Double[] maxBounds, boolean isVertical) {
        Node head = tree.getHead();
        if (Objects.equals(bounds[0], maxBounds[0])) {
            if (Objects.equals(bounds[1], maxBounds[1])) {
                searchInSubtree(head, bounds, isVertical);
            } else {
                topBoundSearch(head, bounds, isVertical);
            }
        } else {
            if (Objects.equals(bounds[1], maxBounds[1])) {
                bottomBoundSearch(head, bounds, isVertical);
            } else {
                searchVSplit(head, bounds, isVertical);
            }
        }
    }

    /**
     * Searches the given node and its subtrees when the bounding box spans across the median.
     *
     * @param node       the node to be searched
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void searchVSplit(Node node, Double[] bounds, boolean isVertical) {
        checkSegment(node.getSegment(), bounds, isVertical);
        if (node.hasLeftChild()) {
            if (node.hasRightChild()) {
                if ((node.getMedian() < bounds[0]) || (node.getMedian() == bounds[0] && node.areEdgesNotEqual())) {
                    searchVSplit(node.getRightChild(), bounds, isVertical);
                } else if ((node.getMedian() > bounds[1]) || (node.getMedian() == bounds[1] && node.areEdgesNotEqual())) {
                    searchVSplit(node.getLeftChild(), bounds, isVertical);
                } else {
                    topBoundSearch(node.getRightChild(), bounds, isVertical);
                    bottomBoundSearch(node.getLeftChild(), bounds, isVertical);
                }
            } else {
                checkSegment(node.getLeftChild().getSegment(), bounds, isVertical);
            }
        }
    }

    /**
     * Checks whether the given segment is within the bounding box and adds it to the reported segments if it is.
     *
     * @param node       the node to be checked
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void topBoundSearch(Node node, Double[] bounds, boolean isVertical) {
        checkSegment(node.getSegment(), bounds, isVertical);
        if (node.hasLeftChild()) {
            if (node.hasRightChild()) {
                if ((node.getMedian() > bounds[1]) || (node.getMedian() == bounds[1] && node.areEdgesNotEqual())) {
                    topBoundSearch(node.getLeftChild(), bounds, isVertical);
                } else {
                    topBoundSearch(node.getRightChild(), bounds, isVertical);
                    searchInSubtree(node.getLeftChild(), bounds, isVertical);
                }
            } else {
                checkSegment(node.getLeftChild().getSegment(), bounds, isVertical);
            }
        }
    }

    /**
     * Searches the given node and its subtrees when the bounding box is below the median.
     *
     * @param node       the node to be searched
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void bottomBoundSearch(Node node, Double[] bounds, boolean isVertical) {
        checkSegment(node.getSegment(), bounds, isVertical);
        if (node.hasLeftChild()) {
            if (node.hasRightChild()) {
                if ((node.getMedian() < bounds[0]) || (node.getMedian() == bounds[0] && node.areEdgesNotEqual())) {
                    bottomBoundSearch(node.getRightChild(), bounds, isVertical);
                } else {
                    bottomBoundSearch(node.getLeftChild(), bounds, isVertical);
                    searchInSubtree(node.getRightChild(), bounds, isVertical);
                }
            } else {
                checkSegment(node.getLeftChild().getSegment(), bounds, isVertical);
            }
        }
    }

    /**
     * Searches the given node and its subtrees.
     *
     * @param node       the node to be searched
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void searchInSubtree(Node node, Double[] bounds, boolean isVertical) {
        checkSegmentXAxis(node.getSegment(), bounds, isVertical);
        if (node.hasLeftChild()) {
            if (node.hasRightChild()) {
                searchInSubtree(node.getRightChild(), bounds, isVertical);
            }
            searchInSubtree(node.getLeftChild(), bounds, isVertical);
        }
    }

    /**
     * Checks whether the given segment is within the bounding box and adds it to the reported segments if it is.
     *
     * @param segment    the segment to be checked
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void checkSegment(Double[] segment, Double[] bounds, boolean isVertical) {
        if (segment[1] >= bounds[0] && segment[1] <= bounds[1]) {
            checkSegmentXAxis(segment, bounds, isVertical);
        }
    }

    /**
     * Checks whether the given segment is within the bounding box and adds it to the reported segments if it is.
     *
     * @param segment    the segment to be checked
     * @param bounds     bounding box array consisting of bottom, top, left and right bounds
     * @param isVertical flag indicating whether the search is vertical or horizontal
     */
    private void checkSegmentXAxis(Double[] segment, Double[] bounds, boolean isVertical) {
        if (segment[0] <= bounds[3] && segment[2] >= bounds[2]) {
            if (isVertical) {
                reportedSegments.add(new Double[]{segment[1], segment[0], segment[3], segment[2]});
            } else {
                reportedSegments.add(segment);
            }
        }
    }
}
