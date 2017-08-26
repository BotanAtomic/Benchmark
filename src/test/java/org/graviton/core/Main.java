package org.graviton.core;

import org.graviton.api.Benchmark;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        BenchmarkProcess.launch(Main.class);
    }

    @Benchmark(unit = TimeUnit.NANOSECONDS, repetition = 5)
    public static void lambdaForEach() {
        Arrays.asList("test", "test1", "test2", "test3").forEach(System.err::println);
    }

    @Benchmark(unit = TimeUnit.NANOSECONDS, repetition = 5)
    public static void basicForEach() {
        for(String string : Arrays.asList("test", "test1", "test2", "test3"))
            System.err.println(string);
    }

}
