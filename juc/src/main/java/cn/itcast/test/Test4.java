package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                    Thread current = Thread.currentThread();
                    boolean interrupted = current.isInterrupted();
                    log.debug("状态终端： {}", interrupted);
                    if (interrupted) {
                        break;
                    }
            }
        }, "t");
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
