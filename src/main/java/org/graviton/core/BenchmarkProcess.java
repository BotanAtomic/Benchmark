package org.graviton.core;

import org.graviton.api.Benchmark;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BenchmarkProcess {

    public static void launch(Class<?> clazz) {
        Stream.of(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Benchmark.class)).forEach(method -> {
            try {
                Benchmark annotation = method.getAnnotation(Benchmark.class);
                if (Modifier.isStatic(method.getModifiers())) {
                    bench(clazz, method, null, annotation);
                } else {
                    if (Stream.of(clazz.getConstructors()).filter(constructor -> constructor.getParameterCount() > 0).count() > 0) {
                        if ((Stream.of(clazz.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).count() == 0)) {
                            System.err.println("Class " + clazz.getName() + " : At least one constructor should not have parameters");
                            return;
                        }
                    }
                    bench(clazz, method, clazz.newInstance(), annotation);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void bench(Class<?> clazz, Method method, Object object, Benchmark annotation) {
        AtomicLong sum = new AtomicLong(0);
        IntStream.range(0, annotation.repetition()).forEach(i -> {
            try {
                long currentTime = System.nanoTime();
                method.invoke(object);
                long difference = System.nanoTime() - currentTime;
                difference = annotation.unit().convert(difference, TimeUnit.NANOSECONDS);
                sum.addAndGet(difference);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("Benchmark result for method " + clazz.getName() + "/" + method.getName() + " : " + (sum.get() / annotation.repetition()) + " " + annotation.unit().name());

    }

}
