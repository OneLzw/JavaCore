package com.easydev.collectin;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 示例 : 将结果收集到映射表
 */
public class CollectingIntoMaps {

    public static class Person {
        private int id;
        private String name;

        public Person (){

        }

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString () {
            return getClass().getName() + "[id=" + id + ",name=" + name +"]";
        }
    }

    public static Stream<Person> people () {
        return Stream.of(new Person(1001 , "Peter") , new Person(1002 , "Tom") , new Person(1003 , "Jerry"));
    }

    public static void main (String[] args) {
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName : " + idToName);

        Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson : " + idToPerson.getClass().getName() + " " + idToPerson);

        idToPerson = people().collect(Collectors.toMap(Person::getId , Function.identity() , (excitingValue , newValue) -> {throw new IllegalStateException();} , TreeMap::new));
        System.out.println("idToPerson : " + idToPerson.getClass().getName() + " " + idToPerson);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
//        locales.collect(Collectors.toMap(locales:: , ));

    }
}
