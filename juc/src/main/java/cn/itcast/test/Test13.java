package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.Test13")
public class Test13 {
    public static void main(String[] args) {
        DecimalAccountCas decimalAccountCas = new DecimalAccountCas(new BigDecimal(10000));
        DecimalAccount.demo(decimalAccountCas);
    }
}

interface DecimalAccount {
    //获取余额
    BigDecimal getBalance();

    //取款
    void withDraw(BigDecimal amount);

    /*
      方法内会启动 1000 个线程，每个线程做 -10 元 的操作
      如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(DecimalAccount account) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withDraw(BigDecimal.TEN);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }

}

class DecimalAccountCas implements DecimalAccount {
//    private final  Object lock = new Object();
    private AtomicReference<BigDecimal> balance;

    public DecimalAccountCas(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withDraw(BigDecimal amount) {
//        synchronized (lock) {
//            BigDecimal balance = this.getBalance();
//            this.balance = balance.subtract(amount);
//        }
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}
