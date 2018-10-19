package com.easydev.interesting;


import java.util.*;

public class GreenWaterFall {

    public static void main(String[] args) {
        ConsoleWaterFall consoleWaterFall = new ConsoleWaterFall();
        consoleWaterFall.waterFall();
    }

}

class ConsoleWaterFall {
    private final int DEFAULT_LINE = 200;
    private final int DEFAULT_SHOW_LENGTH = 10;
    private final int DEFAULT_LENGTH = 20;
    private final int DEFAULT_TIME = 5;
    private final String DEFAULT_STR = "abcdefghijklmnopqrstuvwxyz";
    private Map<Integer , Integer> showLine = new HashMap<>();
    private Map<Integer , Map<Integer , Integer>> showLineData = new LinkedHashMap<>();

    public void waterFall () {
        int a = 0;
        long startTime = new Date().getTime();
        long curTime = new Date().getTime();
        while (startTime + DEFAULT_TIME * 1000 > curTime ) {
            curTime = new Date().getTime();
            initShowLine();
            a++;

            Set<Integer> integers = showLine.keySet();
            for (int i = 0 ; i < DEFAULT_LINE ; i++) {
                if (integers.contains(i)) {
                    Integer showTimes = showLine.get(i);
                    if (showTimes <= DEFAULT_SHOW_LENGTH) {
                        System.out.print(initChar());
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void initShowLine() {
        Map<Integer , Integer> showLine2 = new HashMap<>();
        Set<Integer> integers = showLine.keySet();
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer row = iterator.next();
            Integer showTimes = showLine.get(row);
            if (showTimes < DEFAULT_LENGTH) {
                showLine2.put(row , ++showTimes);
            }
        }
        showLine = showLine2;

        List<Integer> newLineData = initLine();
        Set<Integer> integers1 = showLine.keySet();
        for (int newRow : newLineData) {
            if (integers1.contains(newRow)) {
                continue;
            } else {
                showLine.put(newRow , 1);
            }
        }
    }

    //返回打印数据的位置
    public List<Integer> initLine () {
        List<Integer> showList = new ArrayList<>();
        for (int i = 0 ; i < DEFAULT_LINE; i++) {
            int curLine = (int)(Math.random() * DEFAULT_LINE);
            if (showList.size() > DEFAULT_LINE / 20) {
                break;
            }
            if (showList.contains(curLine)) {
                continue;
            }
            showList.add(curLine);
        }
        return showList;
    }

    public char initChar () {
        int index = (int) (Math.random() * DEFAULT_STR.length());
        char curChar = DEFAULT_STR.charAt(index);
        return curChar;
    }

}
