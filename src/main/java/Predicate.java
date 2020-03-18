interface Predicate<X> extends Function1<X, Boolean> {
    Predicate<Object> ALWAYS_TRUE    = x -> true;
    Predicate<Object> ALWAYS_FALSE   = x -> false;

    Boolean apply(X x);

    default Predicate<X> or(Predicate<? super X> p2) {
        return (X x) -> this.apply(x) || p2.apply(x);
    }

    default Predicate<X> and(Predicate<? super X> p2) {
        return (X x) -> this.apply(x) && p2.apply(x);
    }

    default Predicate<X> not() {
        return (X x) -> !this.apply(x);
    }
}
