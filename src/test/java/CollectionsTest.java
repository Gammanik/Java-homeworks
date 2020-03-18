import org.junit.Test;

import java.util.List;
import java.util.function.BiFunction;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;


public class CollectionsTest {
    private final Function1<Integer, Integer> sucF = x -> x + 1;
    private final Function1<Integer, Integer> mul2F = x -> x * 2;

    private final List<Integer> zeros = asList(0, 0, 0, 0);
    private final List<Integer> ones = asList(1, 1, 1, 1);
    private final List<Integer> twos = asList(2, 2, 2, 2);
    private final List<Integer> fourths = asList(4, 4, 4, 4);

    private final List<Integer> halfOnesZeros =  asList(0, 1, 0, 1, 0, 1, 0, 1);
    private final Predicate<Integer> isZero = x -> x == 0;
    private final Predicate<Integer> isOne = x -> x == 1;

    private final BiFunction<List<Integer>, List<Integer>, Boolean> eq
        = (l1, l2) -> {
        if (l1.size() != l2.size()) { return false; }
        else {
            for (int i = 0; i < l1.size(); i++) {
                if (!l1.get(i).equals(l2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    };

    @Test
    public void mapTest() {
        assertTrue(eq.apply((List<Integer>) Collections.map(sucF, zeros), ones));
        assertArrayEquals(Collections.map(sucF, zeros).toArray(), ones.toArray());
        assertArrayEquals(Collections.map(mul2F, ones).toArray(), twos.toArray());
    }

    @Test
    public void filterTest() {
        assertArrayEquals(Collections.filter(isZero, halfOnesZeros).toArray(),
                zeros.toArray());
        assertArrayEquals(Collections.filter(isOne, halfOnesZeros).toArray(),
                ones.toArray());
        assertArrayEquals(Collections.filter(isOne, fourths).toArray(),
                java.util.Collections.emptyList().toArray());
    }

    @Test
    public void takeWhileTest() {
        assertArrayEquals(Collections.takeWhile(isZero, asList(0, 0, 0, 1, 2, 0)).toArray(),
                asList(0, 0, 0).toArray());
        assertArrayEquals(Collections.takeWhile(isZero, asList(1, 0, 0, 0, 1, 2, 0)).toArray(),
                asList().toArray());
    }

    @Test
    public void takeUntilTest() {
        assertArrayEquals(Collections.takeUntil(isOne, asList(0, 0, 0, 1, 2, 0)).toArray(),
                asList(0, 0, 0).toArray());
        assertArrayEquals(Collections.takeUntil(isZero, asList(1, 0, 0, 0, 1, 2, 0)).toArray(),
                asList(1).toArray());
    }

    @Test
    public void  foldlTest() {
        Function2<Integer, Integer, Integer> szF = (acc, x) -> acc + 1;
        Function2<Integer, Integer, Integer> sumF = Integer::sum;

        assertEquals((int) Collections.foldl(((acc, x) -> acc + 1), 0, asList()), 0);
        assertEquals((int) Collections.foldl((szF), 0, ones), ones.size());
        assertEquals((int) Collections.foldl((szF), 0, halfOnesZeros), halfOnesZeros.size());

        assertEquals((int) Collections.foldl((sumF), 200, ones), ones.size() + 200);
        assertEquals((int) Collections.foldl(((x, y) -> y), 11, asList(1, 2, 3, 4)), 4);
    }

    @Test
    public void foldrTest() {
        assertEquals((int) Collections.foldr(((x, y) -> y), 11, asList(1, 2, 3, 4)), 11);
        assertEquals((int) Collections.foldr(((x, y) -> x), 11, asList(1, 2, 3, 4)), 4);

        Function2<Integer, Integer, Integer> szF = (x, acc) -> acc + 1;
        Function2<Integer, Integer, Integer> sumF = Integer::sum;
        assertEquals((int) Collections.foldr((sumF), 200, ones), ones.size() + 200);

        assertEquals((int) Collections.foldr(((acc, x) -> x + 1), 0, asList()), 0);
        assertEquals((int) Collections.foldr((szF), 0, ones), ones.size());
        assertEquals((int) Collections.foldr((szF), 0, halfOnesZeros), halfOnesZeros.size());
    }

}
