import org.junit.Test;

import static org.junit.Assert.*;

public class PredicateTest {

    final Predicate<Integer> divisibleBy2 = x -> (x % 2) == 0;
    final Predicate<Integer> divisibleBy5 = x -> (x % 5) == 0;
    
    @Test
    public void basicTest() {
        assertTrue(divisibleBy2.apply(0));
        final Function1<Object, Boolean> trueF = Predicate.ALWAYS_TRUE;
        final Function1<Object, Boolean> falseF = Predicate.ALWAYS_FALSE;

        for (int i = 100; i < 300; i = i + 2) {
            assertTrue(divisibleBy2.apply(i));
            assertFalse(divisibleBy2.not().apply(i));

            assertFalse(divisibleBy2.apply(i + 1));
            assertTrue(divisibleBy2.not().apply(i + 1));

            assertTrue(trueF.apply(i));
            assertFalse(falseF.apply(i));
        }
    }

    @Test
    public void andOrTest() {
        final Predicate<Integer> divisibleBy2and5 = divisibleBy2.and(divisibleBy5);
        final Predicate<Integer> divisibleBy2or5 = divisibleBy2.or(divisibleBy5);

        assertTrue(divisibleBy2and5.apply(10));
        assertFalse(divisibleBy2and5.apply(15));
        assertFalse(divisibleBy2and5.apply(222));

        assertTrue(divisibleBy2or5.apply(10));
        assertTrue(divisibleBy2or5.apply(15));
        assertTrue(divisibleBy2or5.apply(222));
    }
}
