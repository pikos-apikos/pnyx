package dev.pnyx.core.domain.result;

import java.util.function.Function;

/**
 * Sealed discriminated union for method results that can fail.
 * <p>
 * Forces callers to handle both success and failure cases at compile time,
 * making business rule violations explicit in method signatures rather than
 * hidden behind unchecked exceptions.
 * <p>
 * This pattern follows the agentic-programming principle that all possible
 * failure modes should be visible in type signatures — see {@code ../docs/99_Reference/REFERENCE_IMPLEMENTATION.md}.
 *
 * @param <V> the success value type
 * @param <E> the error type (typically a sealed error hierarchy)
 * @see ../docs/80_Runtime/INVARIANTS.md
 */
public sealed interface Result<V, E> {

    /** A successful result wrapping a value. */
    record Success<V, E>(V value) implements Result<V, E> {}
    /** A failed result wrapping an error. */
    record Failure<V, E>(E error) implements Result<V, E> {}

    default boolean isSuccess() { return this instanceof Success; }
    default boolean isFailure() { return this instanceof Failure; }

    /**
     * Returns the value if successful, or throws if this is a failure.
     *
     * @return the success value
     * @throws IllegalStateException if this is a Failure
     */
    default V orElseThrow() {
        return switch (this) {
            case Success<V, E>(var v) -> v;
            case Failure<V, E>(var e) -> throw new IllegalStateException("Result was failure: " + e);
        };
    }

    /**
     * Returns the value or throws a custom exception derived from the error.
     *
     * @param exceptionProvider maps the error to a throwable
     * @param <X>               the throwable type
     * @return the success value
     * @throws X if this is a Failure
     */
    default <X extends Throwable> V orElseThrow(Function<? super E, ? extends X> exceptionProvider) throws X {
        return switch (this) {
            case Success<V, E>(var v) -> v;
            case Failure<V, E>(var e) -> throw exceptionProvider.apply(e);
        };
    }

    /**
     * Transforms the success value using the given function, or passes through the failure.
     *
     * @param fn  the mapping function
     * @param <U> the result value type
     * @return mapped Result
     */
    default <U> Result<U, E> map(Function<? super V, ? extends U> fn) {
        return switch (this) {
            case Success<V, E>(var v) -> success(fn.apply(v));
            case Failure<V, E>(var e) -> failure(e);
        };
    }

    /**
     * Chains a result-returning operation on the success value.
     *
     * @param fn  the chaining function
     * @param <U> the result value type
     * @return chained Result
     */
    default <U> Result<U, E> flatMap(Function<? super V, Result<U, E>> fn) {
        return switch (this) {
            case Success<V, E>(var v) -> fn.apply(v);
            case Failure<V, E>(var e) -> failure(e);
        };
    }

    static <V, E> Result<V, E> success(V value) { return new Success<>(value); }
    static <V, E> Result<V, E> failure(E error) { return new Failure<>(error); }
}
