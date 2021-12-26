package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 垃圾袋问题 原子引用 markable
 *
 * @author YuZhouAn on 2021/12/26
 */
@Slf4j(topic = "c.Test15")
public class Test15 {
    public static void main(String[] args) throws InterruptedException {
        GarbageBag bag= new GarbageBag("装满了垃圾");
        // 参数2 mark 可以看作一个标记，表示垃圾袋满了
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);

        log.debug("主线程 start...");
        GarbageBag prev = ref.getReference();
        log.debug(prev.toString());

        new Thread(() -> {
            log.debug("打扫卫生的线程 start...");
            bag.setDesc("空垃圾袋");
            while (!ref.compareAndSet(bag, bag, true, false)) {}
            log.debug(bag.toString());
        }).start();

        Thread.sleep(1000);
        log.debug("主线程想换一只新垃圾袋？");
        boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
        log.debug("换了么？" + success);
        log.debug(ref.getReference().toString());
    }
}

class GarbageBag {
    String desc;

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "desc='" + desc + '\'' +
                '}';
    }
}