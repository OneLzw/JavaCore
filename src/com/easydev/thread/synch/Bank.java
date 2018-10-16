package com.easydev.thread.synch;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private Lock bankLock = new ReentrantLock();
    private Condition sufficientFunds;
    private final double[] accounts;

    public Bank (int n , double initialBalance) {
        sufficientFunds = bankLock.newCondition();
        accounts = new double[n];
        Arrays.fill(accounts , initialBalance);
    }

    public void transfer (int from , int to , double amount) {
        bankLock.lock();
        try {
            if (accounts[from] < amount) {
                sufficientFunds.await();
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out . print (" from " + from + " to " + to +" money is " + amount + " ") ;
            accounts[to] += amount;
            System.out .println("  Total Balance: "  + getTotalBalance());
            sufficientFunds.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }

    }

    public double getTotalBalance() {
        double sum = 0 ;
        for (double a :accounts ) {
            sum += a;
        }
        return sum;
    }

    public int size () {
        return accounts.length;
    }

    /**
     * synchronized关键字修饰方法
     * @param from
     * @param to
     * @param amount
     */
    public synchronized void transfer1 (int from , int to , double amount) {

        try {
            if (accounts[from] < amount) {
                wait();
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out . print (" from " + from + " to " + to +" money is " + amount + " ") ;
            accounts[to] += amount;
            System.out .println("  Total Balance: "  + getTotalBalance());
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}
