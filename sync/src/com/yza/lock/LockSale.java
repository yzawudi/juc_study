package com.yza.lock;

import java.util.concurrent.locks.ReentrantLock;

class Ticket {
    //票数
    private int number = 30;
    //lock
    private final ReentrantLock lock = new ReentrantLock();
    //操作方法：卖票
    public void sale() {
        //上锁
        lock.lock();
        //判断是否有票
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ":卖出：" + number-- + "剩下：" + number);
            }
        } finally {
            //解锁
            lock.unlock();
        }
    }
}

public class LockSale {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()-> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"AA").start();
        new Thread(()-> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"BB").start();
        new Thread(()-> {
            for (int i = 0; i < 30; i++) {
                ticket.sale();
            }
        },"CC").start();
    }
}
