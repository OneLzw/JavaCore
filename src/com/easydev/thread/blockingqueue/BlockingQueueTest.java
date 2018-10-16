package com.easydev.thread.blockingqueue;

import sun.management.FileSystem;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SCARCH_THREADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main (String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter base directory C:/Program Files/Java/jdk1.8.0_144 : ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile) : ");
            String keyword = in.nextLine();

            Runnable enumerator = () -> {
              try {
                  enumerate(new File (directory));
                  queue.put(DUMMY);
              } catch (Exception e) {
                  e.printStackTrace();
              }
            };
            new Thread(enumerator).start();

            for (int i = 1 ; i < SCARCH_THREADS ; i++) {
                Runnable searcher = () -> {
                    try {
                        boolean done = false;
                        while (!done) {
                            File take = queue.take();
                            if (take == DUMMY) {
                                queue.put(take);
                                done = true;
                            } else {
                                search1(take , keyword);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                new Thread(searcher).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void enumerate (File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            } else {
                queue.put(file);
            }
        }
    }

    public static void search (File file , String keyword) throws IOException {
        System.out.println(keyword);
        try (Scanner in = new Scanner(file , "utf-8")) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String msg = in.nextLine();
                if (msg.contains(keyword)) {
                    System.out.printf("%s:%d:%s%n" , file.getPath() , lineNumber , msg);
                } else {
                    System.out.printf("%s:%d:%s%n" , file.getPath() , lineNumber , msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void search1 (File file , String keyword) throws IOException {
        Reader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        int lineNumber = 0;
        String line = "";
        while (line != null) {
            line = reader.readLine();
            lineNumber++;
            if (line != null && line.contains(keyword)) {
                System.out.printf("%s:%d:%s%n" , file.getPath() , lineNumber , line);
            }
        }
    }
}
