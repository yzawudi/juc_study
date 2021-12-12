package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

//thread.sleep()是让线程休眠，也就是暂时中断线程
//在中断过程中难免会出现异常，如果你用记事本编程序，不进行抛出，程序会报错！
//在使用一些软件编写的时候，一般都会提醒 你抛出异常，在Exception类中会有很多异常
//抛出就是让程序强制执行你的代码，异常处理相当于过滤掉了
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                log.debug("sleep 2 s");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    log.debug("dead");
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.debug("interrupt");
        t1.interrupt();
    }
}
