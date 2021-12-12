package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            public Integer call() throws Exception {
                log.debug("running...");
                Thread.sleep(1000);
                return 100;
            }
        });
        Thread thread = new Thread(task);
        thread.start();
    }
}
