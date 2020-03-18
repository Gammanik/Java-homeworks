interface Function2<X, Y, R> {
    R apply(X x, Y y);

    default <R2> Function2<X, Y, R2> compose(Function1<? super R, ? extends R2> g) {
        return (X x, Y y) -> g.apply(this.apply(x, y));
    }

    default Function1<Y, R> bind1(X x) {
        return (Y y) -> this.apply(x, y);
    }

    default Function1<X, R> bind2(Y y) {
        return (X x) -> this.apply(x, y);
    }

    default Function1<X, Function1<Y, R>> curry() {
        return (X x) -> (Y y) -> apply(x, y);
    }

    default Function2<Y, X, R> flip() {
        return (Y y, X x) -> apply(x, y);
    }
}
