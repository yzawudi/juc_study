package cn.itcast.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test12 {
    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Lock lock;
        Condition aC = awaitSignal.newCondition();
        Condition bC = awaitSignal.newCondition();
        Condition cC = awaitSignal.newCondition();

        new Thread(() -> {
            awaitSignal.print("a", aC, bC);
        }).start();

        new Thread(() -> {
            awaitSignal.print("b", bC, cC);
        }).start();

        new Thread(() -> {
            awaitSignal.print("c", cC, aC);
        }).start();

        awaitSignal.lock();

        System.out.println("开始！");
        aC.signal();

        awaitSignal.unlock();
    }
}

class AwaitSignal extends ReentrantLock {

    private Integer loopNumber;

    public AwaitSignal(Integer loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Condition condition, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                condition.await();
                System.out.println(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }
}