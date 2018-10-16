package com.easydev.optinal;

import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
    }
}
