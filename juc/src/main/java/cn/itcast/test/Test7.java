package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                log.debug("开始运行！");
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("结束！");
            }

        }, "t");
        thread.setDaemon(true);
        thread.start();
        log.debug("运行结束！");
    }
}
