package org.graviton.core;

import org.graviton.api.Benchmark;

import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) {
        BenchmarkProcess.launch(Main.class);
    }

    @Benchmark(unit = TimeUnit.SECONDS, repetition = 111)
    public static void test() {
        try {
            Thread.sleep(1);
        } catch (Exception e) {

        }
    }

}
