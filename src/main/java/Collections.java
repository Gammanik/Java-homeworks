import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class Collections {

    public static <X, R> Collection<R> map(Function1<? super X, ? extends R> f, Iterable<? extends X> it) {
        List<R> acc = new ArrayList<>();
        for (X t : it) { acc.add(f.apply(t)); }
        return acc;
    }

    public static <X> Collection<X> filter(Predicate<? super X> predicate, Iterable<? extends X> it) {
        List<X> acc = new ArrayList<>();
        for (X t : it) {
            if (predicate.apply(t)) { acc.add(t); }
        }
        return acc;
    }

    public static <X> Collection<X> takeWhile(Predicate<? super X> predicate, Iterable<? extends X> it) {
        List<X> acc = new ArrayList<>();
        for (X t : it) {
            if (!predicate.apply(t)) {
                return acc;
            }

            acc.add(t);
        }
        return acc;
    }

    public static <X> Collection<X> takeUntil(Predicate<? super X> predicate,
                                              Iterable<? extends X> it) {
        return takeWhile(predicate.not(), it);
    }

    public static <X, Y> X foldl(Function2<? super X, ? super Y, ? extends X> func, X zero, Iterable<? extends Y> it) {
        X acc = zero;
        for (Y curr : it) {
            acc = func.apply(acc, curr);
        }

        return acc;
    }

    public static <X, Y> Y foldr(Function2<? super X, ? super Y, ? extends Y> func, Y zero, Iterable<? extends X> it) {
        Stack<X> stack = new Stack<>();
        for (X curr : it) {
            stack.add(curr);
        }

        Y acc = zero;
        for (X curr : stack) {
            acc = func.apply(curr, acc);
        }
        return acc;
    }


}
