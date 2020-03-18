interface Function1<X, R> {

    R apply(X x);

    default <R2> Function1<X, R2> compose(Function1<R, ? extends R2> g) {
        return (X _x) -> g.apply(this.apply(_x));
    }
}
