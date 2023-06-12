package windowing;

import gui.Gui;
import tools.SegmentsList;
import tools.Sort;
import tree.PrioritySearchTree;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Windowing class represents the main class of the application. It is responsible for parsing the input file,
 * storing the segments, constructing the vertical and horizontal priority search trees, and calling the windowing
 * algorithm to find the segments that intersect the chosen window.
 */
public final class Windowing {
    private static Windowing instance;
    private static PrioritySearchTree vTree, hTree;
    private final Gui gui;
    private ArrayList<Double[]> segments = new ArrayList<>();
    private double min_x, max_x, min_y, max_y;
    private double chosen_min_x, chosen_max_x, chosen_min_y, chosen_max_y;

    private Windowing(Gui gui) {
        this.gui = gui;
        this.min_x = -1000;
        this.max_x = 1000;
        this.min_y = -1000;
        this.max_y = 1000;
    }

    /**
     * Initializes the Windowing instance with the given Gui object.
     *
     * @param gui the Gui object to use
     */
    public static void init(Gui gui) {
        if (instance == null)
            instance = new Windowing(gui);
    }

    /**
     * Gets the Windowing instance.
     *
     * @return the Windowing instance
     */
    public static Windowing getInstance() {
        return instance;
    }

    /**
     * Reads and parses the input file.
     *
     * @param file the input file
     * @return a message indicating the window size and number of segments, or null if an error occurred
     */
    public String readFile(File file) {
        try {
            LocalTime start = LocalTime.now();
            Scanner input = new Scanner(file);
            min_x = Float.parseFloat(input.next());
            max_x = Float.parseFloat(input.next());
            min_y = Float.parseFloat(input.next());
            max_y = Float.parseFloat(input.next());
            chosen_min_x = min_x;
            chosen_max_x = max_x;
            chosen_min_y = min_y;
            chosen_max_y = max_y;

            ArrayList<Double[]> vArrayList = new ArrayList<>();
            ArrayList<Double[]> hArrayList = new ArrayList<>();
            segments = new ArrayList<>();

            while (input.hasNext()) {
                double x1 = Float.parseFloat(input.next());
                double y1 = Float.parseFloat(input.next());
                double x2 = Float.parseFloat(input.next());
                double y2 = Float.parseFloat(input.next());
                segments.add(new Double[]{x1, y1, x2, y2});
                if (x1 == x2) {
                    if (y1 < y2) {
                        vArrayList.add(new Double[]{y1, x1, y2, x2});
                    } else {
                        vArrayList.add(new Double[]{y2, x2, y1, x1});
                    }
                } else if (y1 == y2) {
                    if (x1 < x2) {
                        hArrayList.add(new Double[]{x1, y1, x2, y2});
                    } else {
                        hArrayList.add(new Double[]{x2, y2, x1, y1});
                    }
                } else {
                    System.out.println("Not a segment");
                    return null;
                }
            }
            input.close();
            LocalTime step1 = LocalTime.now();
            Sort sort = new Sort();
            sort.quickSort(vArrayList, 0, vArrayList.size() - 1);
            sort.quickSort(hArrayList, 0, hArrayList.size() - 1);
            SegmentsList vSegments = new SegmentsList(vArrayList);
            SegmentsList hSegments = new SegmentsList(hArrayList);
            LocalTime step2 = LocalTime.now();
            Windowing.vTree = new PrioritySearchTree(vSegments);
            Windowing.hTree = new PrioritySearchTree(hSegments);
            LocalTime end = LocalTime.now();
            System.out.println("Time to read the file: " + Duration.between(start, step1).toMillis() + " ms | " +
                    "Time to sort the segments: " + Duration.between(step1, step2).toMillis() + " ms | " +
                    "Time to create Tree: " + Duration.between(step2, end).toMillis() + " ms");
            return "Window Size: [" + min_x + ", " + max_x + "] x [" + min_y + ", " + max_y + "], Number of segments: "
                    + segments.size() + "";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * The function sets the value of the variables `chosen_min_x`, `chosen_max_x`, `chosen_min_y` and `chosen_max_y`.
     *
     * @param chosen_min_x The value of the variable `chosen_min_x`.
     * @param chosen_max_x The value of the variable `chosen_max_x`.
     * @param chosen_min_y The value of the variable `chosen_min_y`.
     * @param chosen_max_y The value of the variable `chosen_max_y`.
     * @return The method returns true if the chosen window is within the window size, and false otherwise.
     */
    public boolean choseSize(double chosen_min_x, double chosen_max_x, double chosen_min_y, double chosen_max_y) {
        if (chosen_min_x < min_x || chosen_max_x > max_x || chosen_min_y < min_y || chosen_max_y > max_y)
            return false;
        this.chosen_min_x = chosen_min_x;
        this.chosen_max_x = chosen_max_x;
        this.chosen_min_y = chosen_min_y;
        this.chosen_max_y = chosen_max_y;
        return true;
    }

    /**
     * The function requests and displays line segments within a specified window using a windowing
     * algorithm.
     *
     * @return The method is returning a string that contains information about the window size and the
     * number of reported segments.
     */
    public String requestLines() {
        LocalTime  start = LocalTime.now();
        segments = new WindowingAlgorithm().launchWindowing(vTree, hTree,
                new Double[]{chosen_min_y, chosen_max_y, chosen_min_x, chosen_max_x},
                new Double[]{min_y, max_y, min_x, max_x});
        LocalTime end = LocalTime.now();
        System.out.println("Time to windowing (nb of seg: " + segments.size() + "): "
                + Duration.between(start, end).toMillis() + " ms");
        gui.drawLines(segments);
        return "Window Size: [" + min_x + ", " + max_x + "] x [" + min_y + ", " + max_y +
                "], Number of reported segments: " + segments.size() + "";
    }


    /**
     * The function returns the value of the variable `chosen_min_x`.
     *
     * @return The value of the variable `chosen_min_x`.
     */
    public double getChosenMinX() {
        return chosen_min_x;
    }

    /**
     * The function returns the value of the variable `chosen_max_x`.
     *
     * @return The value of the variable `chosen_max_x`.
     */
    public double getChosenMaxX() {
        return chosen_max_x;
    }

    /**
     * The function returns the value of the variable `chosen_min_y`.
     *
     * @return The value of the variable `chosen_min_y`.
     */
    public double getChosenMinY() {
        return chosen_min_y;
    }

    /**
     * The function returns the value of the variable `chosen_max_y`.
     *
     * @return The value of the variable `chosen_max_y`.
     */
    public double getChosenMaxY() {
        return chosen_max_y;
    }

    /**
     * The function returns the value of the variable `min_x`.
     *
     * @return The value of the variable `min_x`.
     */
    public double getMinX() {
        return min_x;
    }

    /**
     * The function returns the value of the variable `max_x`.
     *
     * @return The value of the variable `max_x`.
     */
    public double getMaxX() {
        return max_x;
    }

    /**
     * The function returns the value of the variable `min_y`.
     *
     * @return The value of the variable `min_y`.
     */
    public double getMinY() {
        return min_y;
    }

    /**
     * The function returns the value of the variable `max_y`.
     *
     * @return The value of the variable `max_y`.
     */
    public double getMaxY() {
        return max_y;
    }

    /**
     * The function returns the number of segments.
     *
     * @return The number of segments.
     */
    public double getSegmentsSize() {
        return segments.size();
    }
}
