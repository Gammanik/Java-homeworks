import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Function2Test {

    private final Function1<Double, Double> div2F = x -> x / 2;
    private final Function2<Integer, Integer, Integer> sumF = Integer::sum;
    private final Function2<Integer, Integer, Double> divF = (x, y) ->  ((double) x / (double) y);
    private final Double eps = 1.0E-10;

    @Test
    public void testApply() {
        assertEquals(sumF.apply(2, 2), (Integer) 4);
        assertEquals(sumF.apply(0, 0), (Integer) 0);

        assertEquals(divF.apply(2, 2), (Double) 1.0);
        assertEquals(divF.apply(0, 2), (Double) 0.0);
    }

    @Test
    public void testCompose() {
        // divDiv2F = (x / y) / 2
        Function2<Integer, Integer, Double> divDiv2F = divF.compose(div2F);
        assertEquals(divDiv2F.apply(2,1), (Double) 1.0);
        assertEquals(divDiv2F.apply(4, 2), (Double) 1.0);
    }

    @Test
    public void testBind1() {
        final Function1<Integer, Integer> plus42F = sumF.bind1(42);
        assertEquals(plus42F.apply(0), (Integer) 42);
        assertEquals(plus42F.apply(-442), (Integer) (-400));
        assertEquals(plus42F.apply(232), plus42F.apply(232));

        final Function1<Integer, Double> div12byF = divF.bind1(12);
        assertEquals(div12byF.apply(1), 12.0, eps);
        assertEquals(div12byF.apply(3), 4.0, eps);
        assertEquals(div12byF.apply(5), 2.4, eps);
    }

    @Test
    public void testBind2() {
        final Function1<Integer, Integer> plus42F = sumF.bind2(42);
        assertEquals(plus42F.apply(0), (Integer) 42);
        assertEquals(plus42F.apply(-442), (Integer) (-400));
        assertEquals(plus42F.apply(232), plus42F.apply(232));

        final Function1<Integer, Double> div12F = divF.bind2(12);
        assertEquals(div12F.apply(12), 1.0, eps);
        assertEquals(div12F.apply(240), 20.0, eps);
        assertEquals(div12F.apply(320), 26.66666666666, eps);
    }

    @Test
    public void testCurry() {
        assertEquals(sumF.curry().apply(2).apply(2), (Integer) 4);
        assertEquals(divF.curry().apply(20).apply(10), 2, eps);
        assertNotEquals(divF.curry().apply(20).apply(10), divF.curry().apply(10).apply(20));
    }

    @Test
    public void testFlip() {
        assertEquals(sumF.flip().apply(20, 20), (Integer) 40);
        assertEquals(divF.flip().apply(2, 4), (Double) 2.0);
        assertEquals(divF.curry().apply(20).apply(10),
              divF.flip().curry().apply(10).apply(20));
    }
}
