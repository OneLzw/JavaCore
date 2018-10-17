package com.easydev.optinal;

import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OptionalTest {

    public static void main (String[] args) throws IOException {
        String msg = new String(Files.readAllBytes(Paths.get("E:/TestFile/测试1.txt")), StandardCharsets.UTF_8);
        List<String> strArray = Arrays.asList(msg.split("\\PL"));

        Optional<String> findFirst = strArray.stream().filter(s -> s.contains("在")).findFirst();
        System.out.println(findFirst.orElse("no word") + " contains 在");

        Optional<Object> emptyOption = Optional.empty();
        Object result = emptyOption.orElse("N/A");
        System.out.println("result is " + result);

        result = emptyOption.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result is " + result);

        try {
            result = emptyOption.orElseThrow(NewLocalException::new);
            System.out.println("result is " + result);
        } catch (NewLocalException e) {
            e.printStackTrace();
        }

        findFirst = strArray.stream().filter(s -> s.contains("在")).findFirst();
        findFirst.ifPresent(s -> System.out.println(s + " contain 在2"));

        HashSet<Object> results = new HashSet<>();
        findFirst.ifPresent(results::add);
        Optional<Boolean> addResult = findFirst.map(results::add);
        System.out.println("s : " + addResult);

        System.out.println("1 : " + inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println("2 : " + inverse(-4.0).flatMap(OptionalTest::squareRoot));
        System.out.println("3 : " + inverse(0.0).flatMap(OptionalTest::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println("4 : " + result2);
    }

    public static Optional<Double> inverse (Double d) {
        return d == 0 ? Optional.empty() : Optional.of(1 / d);
    }

    public static Optional<Double> squareRoot (Double d) {
        return d < 0 ? Optional.empty() : Optional.of(Math.sqrt(d));
    }
}
