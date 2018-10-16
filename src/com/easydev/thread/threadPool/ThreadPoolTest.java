package com.easydev.thread.threadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        Date startDate = null;

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("new Directory : ");
            String directory = in.nextLine();
            System.out.println("keyWork : ");
            String keyword = in.nextLine();

            startDate = new Date();
            System.out.println("startDate : " + startDate.getTime());

            //需要时创建线程，释放后线程保持60s
            ExecutorService pool = Executors.newCachedThreadPool();
            MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
            Future<Integer> result = pool.submit(counter);

            try {
                System.out.println(result.get() + " match files");

            } catch (Exception e) {
                e.printStackTrace();
            }
            pool.shutdown();

            int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
            System.out.println("largestPoolSize is " + largestPoolSize);
        }
        Date endDate = new Date();
        System.out.println("endDate : " + endDate.getTime());
        System.out.println("time : " + (endDate.getTime() - startDate.getTime()));
    }
}

class MatchCounter implements Callable<Integer> {

    private File directory;
    private String keyWord;
    private ExecutorService pool;
    private int count;

    public MatchCounter() {

    }

    public MatchCounter(File directory, String keyWord, ExecutorService pool) {
        this.directory = directory;
        this.keyWord = keyWord;
        this.pool = pool;
    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        MatchCounter counter = new MatchCounter(file, keyWord, pool);
                        Future<Integer> result = pool.submit(counter);
                        if (result != null) {
                            results.add(result);
                        }
                    } else {
                        if (search(file)) {
                            count ++;
                        }
                    }
                }
            }

            for (Future<Integer> result : results) {
                try {
                    count += result.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean search (File file) {
        try {
            try (Scanner in = new Scanner(file , "utf-8")) {
                boolean found = false;
                while (!found && in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.contains(keyWord)) {
                        found = true;
                    }
                }
                return found;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
