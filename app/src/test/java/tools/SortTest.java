package tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class SortTest {
    @Test
    public void quickSortTest(){
        Sort sort = new Sort();
        ArrayList<Double[]> list = createList();
        sort.quickSort(list, 0, list.size()-1);
        for (int i = 0; i < list.size()-1; i++){
            Assertions.assertTrue(list.get(i)[1] <= list.get(i+1)[1]);
        }
    }

    private ArrayList<Double[]> createList(){
        ArrayList<Double[]> list = new ArrayList<>();
        Double[] segment1 = new Double[]{167.0, 203.0, -894.0, 203.0};
        Double[] segment2 = new Double[]{-97.0, -16.0, 418.0, -16.0};
        Double[] segment3 = new Double[]{-328.0, 574.0, -613.0, 574.0};
        Double[] segment4 = new Double[]{-640.0, 873.0, 402.0, 873.0};
        Double[] segment5 = new Double[]{-804.0, 34.0, -804.0, 759.0};
        Double[] segment6 = new Double[]{-879.0, 825.0, -879.0, 406.0};
        Double[] segment7 = new Double[]{-564.0, 981.0, -394.0, 981.0};
        Double[] segment8 = new Double[]{-289.0, 701.0, 483.0, 701.0};
        Double[] segment9 = new Double[]{666.0, 823.0, 666.0, 125.0};
        Double[] segment10 = new Double[]{-266.0, -504.0, -454.0, -504.0};
        Double[] segment11 = new Double[]{-502.0, -477.0, 414.0, -477.0};
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
