package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Test5")
public class Test6 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.debug("park!");
            LockSupport.park();
            log.debug("Unpark!");
            log.debug("打断状态：{}",Thread.interrupted());

            LockSupport.park();
            log.debug("Unpark!");
        }, "t");
        thread.start();
        Thread.sleep(1);
        thread.interrupt();
    }
}
