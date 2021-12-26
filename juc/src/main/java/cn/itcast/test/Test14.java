package cn.itcast.test;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA 问题解决
 */
@Slf4j(topic = "c.Test14")
public class Test14 {
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);
    public static void main(String[] args)  throws InterruptedException {
        log.debug("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.getReference();
        //获取版本号
        int stamp = ref.getStamp();
        log.debug("版本 {}", stamp);

        other();
        Thread.sleep(1000);

        // 尝试改为 C
        log.debug("change A->C {}", ref.compareAndSet(prev, "C",stamp, stamp+1));


    }
    private static void other() {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B", ref.getStamp(), ref.getStamp()+1));
        }, "t1").start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A", ref.getStamp(), ref.getStamp()+1));
        }, "t2").start();
    }
}

