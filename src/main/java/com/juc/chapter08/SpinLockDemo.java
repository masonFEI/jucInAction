/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter08;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 *  题目：实现一个自旋锁，复习CAS思想
 *  自旋锁好处：循环比较获取没有类似wait的阻塞
 * 
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-20 22:20
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();

        while (!atomicReference.compareAndSet(null, thread)) {

        }

        System.out.println(thread.getName() + "\t come in");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);

        System.out.println(thread.getName() + "\t task over, unlock");
    }

    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            // 暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLockDemo.unlock();
        }, "A").start();

        // 暂停500ms,线程A先于B启动
        new Thread(() -> {
            spinLockDemo.lock();
            // 暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLockDemo.unlock();
        }, "B").start();

    }

}
