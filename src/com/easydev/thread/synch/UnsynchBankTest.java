package com.easydev.thread.synch;

/**
 * 同步
 * 多线程同时修改一个数据，导致数据不一致
 */
public class UnsynchBankTest {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;

    public static void main (String[] args) {
        Bank bank = new Bank(NACCOUNTS , INITIAL_BALANCE);
        for (int i = 0 ; i < NACCOUNTS ; i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    while(true) {
                        int toAccount = (int)(bank.size() * Math.random());
                        double amout = MAX_AMOUNT * Math.random();
                        bank.transfer(fromAccount , toAccount , amout);
                        Thread.sleep((int)(DELAY * Math.random()));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            };
            Thread t = new Thread(r);
            t.start();
        }
    }
}
