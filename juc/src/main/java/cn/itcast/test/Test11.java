package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test11")
public class Test11 {
    static final Object lock = new Object();
    static Boolean r2 = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!r2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                r2 = true;
                lock.notify();
            }
        });
        t1.start();
        t2.start();
    }
}
