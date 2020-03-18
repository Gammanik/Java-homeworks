import java.util.function.Function;

interface Function2<X, Y, R> { // extends Function1<X, R> {

    R apply(X x, Y y);

//    <T, R> Function2(Function<? super T, R> f) {
//        super(f);
//    }
}
