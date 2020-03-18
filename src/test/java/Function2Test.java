import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function2Test {

    private final Function2<Integer, Integer, Integer> sumF = Integer::sum;
    private final Function2<Integer, Integer, Double> divF = (x, y) -> (double) (x / y);

    @Test
    public void testApply() {
        assertEquals(sumF.apply(2, 2), (Integer) 4);
        assertEquals(sumF.apply(0, 0), (Integer) 0);

        assertEquals(divF.apply(2, 2), (Double) 1.0);
        assertEquals(divF.apply(0, 2), (Double) 0.0);
    }
}
