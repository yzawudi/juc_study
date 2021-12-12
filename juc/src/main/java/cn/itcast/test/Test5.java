package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Test5")
public class Test5 {
    public static void main(String[] args)  throws InterruptedException {
        TwoStageTerminationMode twoStageTerminationMode = new TwoStageTerminationMode();
        twoStageTerminationMode.test();
        TimeUnit.SECONDS.sleep(2);
        twoStageTerminationMode.stop();
    }
}
@Slf4j(topic = "c.TwoStageTerminationMode")
class TwoStageTerminationMode {
    private Thread monitor;
    public void test() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("监控中！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        monitor.start();
    }
    public void stop() {
        monitor.interrupt();
    }
}
