package windowing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.SegmentsList;
import tree.PrioritySearchTree;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class WindowingAlgorithmTest {
    Double[] maxBounds;

    @Test
    public void launchWindowingTest(){
        ArrayList<Double[]> hArrayList = createHList();
        ArrayList<Double[]> vArrayList = createVList();
        SegmentsList hList = new SegmentsList(hArrayList);
        SegmentsList vList = new SegmentsList(vArrayList);
        PrioritySearchTree hTree = new PrioritySearchTree(hList);
        PrioritySearchTree vTree = new PrioritySearchTree(vList);
        maxBounds = new Double[]{-1000.0, 1000.0, -1000.0, 1000.0};
        Double[] bounds1 = new Double[]{-222.3, 560.0, -600.0, 1000.0};
        Double[] bounds2 = new Double[]{-252.6, -160.0, 600.0, 1000.0};
        Double[] bounds3 = new Double[]{722.3, 1000.0, -1000.0, 1000.0};
        Double[] bounds4 = new Double[]{429.3, 963.0, -1000.0, 500.0};
        Double[] bounds5 = new Double[]{-1000.0, 1000.0, -1000.0, 1000.0};
        checkSegments(vTree, hTree, bounds1);
        checkSegments(vTree, hTree, bounds2);
        checkSegments(vTree, hTree, bounds3);
        checkSegments(vTree, hTree, bounds4);
        checkSegments(vTree, hTree, bounds5);
    }

    private void checkSegments(PrioritySearchTree vTree, PrioritySearchTree hTree, Double[] bounds){
        ArrayList<Double[]> reportedSegments = new WindowingAlgorithm().launchWindowing(vTree, hTree, bounds, maxBounds);
        for (Double[] reportedSegment : reportedSegments){
            if (Objects.equals(reportedSegment[0], reportedSegment[2])){
                checkSegment(reportedSegment, bounds, true);
            }else if(Objects.equals(reportedSegment[1], reportedSegment[3])){
                checkSegment(reportedSegment, bounds, false);
            }else{
                fail();
            }
        }
    }

    private void checkSegment(Double[] segment, Double[] bounds, boolean isVertical){
        if (isVertical){
            Assertions.assertTrue(segment[0] >= bounds[2] && segment[0] <= bounds[3]);
            if (segment[1] < segment[3]){
                Assertions.assertTrue(segment[1] <= bounds[1]);
                Assertions.assertTrue(segment[3] >= bounds[0]);
            }else{
                Assertions.assertTrue(segment[3] <= bounds[1]);
                Assertions.assertTrue(segment[1] >= bounds[0]);
            }
        }else{
            Assertions.assertTrue(segment[1] >= bounds[0] && segment[1] <= bounds[1]);
            if (segment[0] < segment[2]){
                Assertions.assertTrue(segment[0] <= bounds[3]);
                Assertions.assertTrue(segment[2] >= bounds[2]);
            }else{
                Assertions.assertTrue(segment[2] <= bounds[3]);
                Assertions.assertTrue(segment[0] >= bounds[2]);
            }
        }
    }

    private ArrayList<Double[]> createHList(){
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

    private ArrayList<Double[]> createVList(){
        ArrayList<Double[]> list = new ArrayList<>();
        Double[] segment1 = new Double[]{-268.0, -878.0, 69.0, -878.0};
        Double[] segment2 = new Double[]{-762.0, -787.0, -402.0, -787.0};
        Double[] segment3 = new Double[]{-624.0, -698.0, 447.0, -698.0};
        Double[] segment4 = new Double[]{-410.0, -503.0, 235.0, -503.0};
        Double[] segment5 = new Double[]{249.0, -356.0, 953.0, -356.0};
        Double[] segment6 = new Double[]{-724.0, -191.0, -601.0, -191.0};
        Double[] segment7 = new Double[]{-110.0, 313.0, 562.0, 313.0};
        Double[] segment8 = new Double[]{-700.0, 317.0, 939.0, 317.0};
        Double[] segment9 = new Double[]{-477.0, 332.0, -396.0, 332.0};
        Double[] segment10 = new Double[]{-974.0, 733.0, -98.0, 733.0};
        Double[] segment11 = new Double[]{-319.0, 995.0, 69.0, 995.0};
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
