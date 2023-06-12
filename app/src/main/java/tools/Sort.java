package tools;

import java.util.ArrayList;

/**
 * The Sort class contains a quickSort method that sorts an ArrayList of Double arrays based on their x
 * or y values.
 */
public class Sort {
    public Sort() {
    }

    /**
     * This is a recursive implementation of the quicksort algorithm to sort an ArrayList of Double
     * arrays.
     *
     * @param segments An ArrayList of Double arrays representing segments to be sorted.
     * @param start    The index of the first element in the ArrayList to be sorted.
     * @param end      The "end" parameter in the "quickSort" method represents the index of the last
     *                 element in the ArrayList "segments" that needs to be sorted. It is the upper bound of the
     *                 subarray that needs to be sorted.
     */
    public void quickSort(ArrayList<Double[]> segments, int start, int end) {
        if (start < end) {
            int pivot = partition(segments, start, end);
            quickSort(segments, start, pivot - 1);
            quickSort(segments, pivot + 1, end);
        }
    }

    /**
     * This function partitions an ArrayList of Double arrays based on the value of the second element
     * in each array.
     *
     * @param segments An ArrayList of Double arrays representing line segments. Each Double array
     *                 contains two elements: the starting point and the ending point of the line segment.
     * @param start    The index of the first element in the ArrayList to be considered in the
     *                 partitioning process.
     * @param end      The "end" parameter in the given code represents the index of the last element in the
     *                 ArrayList "segments" that needs to be considered for partitioning. It is the upper bound of the
     *                 range of elements that are being partitioned.
     * @return The method is returning an integer value which is the index of the pivot element after
     * partitioning the ArrayList of Double arrays.
     */
    private int partition(ArrayList<Double[]> segments, int start, int end) {
        int pivot = start;
        for (int i = start; i < end; i++) {
            if (segments.get(i)[1] <= segments.get(end)[1]) {
                swap(segments, i, pivot);
                pivot += 1;
            }
        }
        swap(segments, end, pivot);
        return pivot;
    }

    /**
     * This function swaps two elements in an ArrayList of Double arrays.
     *
     * @param segments An ArrayList of Double arrays. Each Double array represents a segment with two
     *                 elements, the start and end points of the segment.
     * @param first    The index of the first element to be swapped in the ArrayList.
     * @param second   The parameter "second" is an integer representing the index of the second element
     *                 to be swapped in the ArrayList "segments".
     */
    private void swap(ArrayList<Double[]> segments, int first, int second) {
        Double[] temp = segments.get(first);
        segments.set(first, segments.get(second));
        segments.set(second, temp);
    }
}
