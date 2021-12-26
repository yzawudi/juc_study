package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新器
 *
 * @author YuZhouAn on 2021/12/26
 */
@Slf4j
public class Test16 {
    private volatile int field;

    public static void main(String[] args) {
        Class tclass;

        AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Test16.class, "field");
        Test16 test16 = new Test16();
        fieldUpdater.compareAndSet(test16, 0, 10);
        // 修改成功 field = 10
        System.out.println(test16.field);
        // 修改成功 field = 20
        fieldUpdater.compareAndSet(test16, 10, 20);
        System.out.println(test16.field);
        // 修改失败 field = 20
        fieldUpdater.compareAndSet(test16, 20, 30);
        System.out.println(test16.field);
    }
}