import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Function1Test {

    private final Function1<Integer, Integer> sucF = x -> x + 1;
    private final Function1<Integer, Integer> mulTwoF = x -> x * 2;

    @Test
    public void testApply() {
        assertEquals(sucF.apply(0), (Integer) 1);
        assertEquals(sucF.apply(1), (Integer) 2);
    }

    @Test
    public void testCompose() {
        Function1<Integer, Integer> sucMulF = sucF.compose(mulTwoF);
        assertEquals(sucMulF.apply(2), (Integer) 6);
    }
}
