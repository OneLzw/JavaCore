package com.easydev.collecting;

import org.junit.Test;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingResults {
    public static Stream<String> noVowels(){
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get("E:/TestFile/测试1.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> listWords = Arrays.asList(contents.split("\\PL"));
        Stream<String> stringStream = listWords.stream();

        Stream<String> result = stringStream.map(s -> s.replaceAll("在", ""));
        return result;
    }

    public static <T> void show(String label , Set<T> set) {
        System.out.println(label + " ; " + set.getClass().getName());
        System.out.println("[" + set.stream().limit(10).map(Object::toString).collect(Collectors.joining(",")) +"]");
    }

    public static  void main(String[] args) {
        Iterator<Integer> iterator = Stream.iterate(0, n -> n + 1).limit(10).iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        Object[] objects = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println("object Array " + objects);

        try {
            Integer number = (Integer) objects[0];
            System.out.println("number is " + number);
            System.out.println("next number is an Exception");
            Integer[] integers = (Integer[]) objects;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }


        Integer[] integers = Stream.iterate(0, n -> n + 1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array " + integers);

        Set<String> noVowelSet = noVowels().limit(10).collect(Collectors.toSet());
        show ("noVowelSet" , noVowelSet);

        TreeSet<String> treeSet = noVowels().limit(10).collect(Collectors.toCollection(TreeSet::new));
        show ("treeSet" , treeSet);

        String str = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("str " + str);

        String str1 = noVowels().limit(10).collect(Collectors.joining(","));
        System.out.println("str1 " + str1);

        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        System.out.println("maxLength is " + summary.getMax());
        System.out.println("minLength is " + summary.getMin());

        System.out.println("Foreach ; ");
        noVowels().forEach(System.out::println);
    }

    @Test
    public void test1 () {
        int line = 0;
        while (line < 5000) {
            for (int i = 0 ; i < 200 ; i++) {
                int i1 = Math.random() > 0.5 ? 0 : 1;
                System.out.print("\033[40;32m" + i1 +"\033[5m");

            }
            line++;
            System.out.println();
        }
    }
}
