/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadDemo2
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-22 22:35
 */
// 第一步 创建资源类，定义属性和操作方法
class Share {
    private int       number    = 0;

    // 创建Lock
    private Lock      lock      = new ReentrantLock();
    private Condition condition = lock.newCondition();

    // +1
    public void incr() throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (number != 0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    // -1
    public void decr() throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 判断
            while (number != 1) {
                condition.await();
            }
            // 干活
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}

public class ThreadDemo2 {

    public static void main(String[] args) {
        final Share share = new Share();

        // 创建线程，执行+1操作
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        // 创建线程，执行-1操作
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

    }
}
