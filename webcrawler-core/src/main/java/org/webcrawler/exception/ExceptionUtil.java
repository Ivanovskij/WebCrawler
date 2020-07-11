package org.webcrawler.exception;

import java.util.function.Function;

public class ExceptionUtil {

    public static <T, R> Function<T, R> rethrowFunction(FunctionWithExceptions<T, R> function) {
        return t -> {
            try { return function.apply(t); }
            catch (Exception exception) { throwAsUnchecked(exception); return null; }
        };
    }

    @FunctionalInterface
    public interface FunctionWithExceptions<T, R> {
        R apply(T t) throws Exception;
    }

    @SuppressWarnings ("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E { throw (E)exception; }

}
