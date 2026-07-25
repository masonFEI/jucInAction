/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter13;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQSDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-21 19:58
 */
public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();// 非公平锁

        // A B C 三个顾客，去银行办理业务，A先到。此时窗口空无一人，他优先获得办理窗口的机会，办理业务
        // A 耗时严重，估计长期占有窗口
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("--- come in A");
                // 暂停20分钟线程
                try {
                    TimeUnit.MINUTES.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                reentrantLock.unlock();
            }
        }, "A").start();

        // B是第2个顾客，B一看到受理窗口被A占用，只能去候客区等待，进入AQS队列，等待着A办理完成，尝试去抢占受理窗口
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("--- come in B");
            } finally {
                reentrantLock.unlock();
            }
        }, "B").start();

        // C是第3个顾客，C一看到受理窗口被A占用，只能去候客区等待，进入AQS队列，等待着A办理完成，尝试去抢占受理窗口,前面是B顾客，FIFO
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println("--- come in C");
            } finally {
                reentrantLock.unlock();
            }
        }, "C").start();

        // 后续顾客DEFG... 以此类推
        new Thread(() -> {
            reentrantLock.lock();
            try {

            } finally {
                reentrantLock.unlock();
            }
        }, "D").start();


    }

    private void aqsDemo() {
        // 内部 sync 继承自AQS
        new ReentrantLock();

        // 内部 sync 继承自AQS
        new CountDownLatch(10);

        // 内部 sync 继承自AQS
        new Semaphore(10);
    }

}
