/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter08;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABADemo,多线程情况下的ABA问题
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-20 22:51
 */
public class ABADemo {

    /** AtomicInteger的value已经是volatile的了 */
    static AtomicInteger                   atomicInteger    = new AtomicInteger(100);

    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        // abaHappen();

        abaResolve();

    }

    /**
     * 解决aba问题
     */
    private static void abaResolve() {
        new Thread(() -> {
            int stamp = stampedReference.getStamp();

            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);

            // 暂停500ms,保证后面的t4线程初始化拿到的版本号和我一样
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName() + "\t第2次版本号：" + stampedReference.getStamp());

            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp() + 1);

            System.out.println(Thread.currentThread().getName() + "\t第3次版本号：" + stampedReference.getStamp());

        }, "t3").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号：" + stamp);

            // 暂停1秒钟，等待上面的t3线程完成一次ABA操作
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = stampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            System.out.println(b + "\t" + stampedReference.getStamp() + "\t" + stampedReference.getReference());
        }, "t4").start();
    }

    /**
     * aba问题 发生
     */
    private static void abaHappen() {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicInteger.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(atomicInteger.compareAndSet(100, 2022) + "\t" + atomicInteger.get());
        }, "t2").start();
    }

}
