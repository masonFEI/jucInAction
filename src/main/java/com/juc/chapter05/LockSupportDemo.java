/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LockSupportDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-04 22:29
 */
public class LockSupportDemo {

    public static void main(String[] args) {

    }

    private static void syncParkUnpark() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t---come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t---被唤醒");
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t---发出通知");
        }, "t2").start();
    }

    private static void syncAwaitSignal() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t---被唤醒");
            } catch (Exception e) {
                lock.unlock();
            }

        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t---发出通知");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() {
        Object objectLock = new Object();// 同一把锁，类似同一个资源

        new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t---come in");
                try {
                    // 停滞
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "\t---被唤醒");
            }
        }, "t1").start();

        // try {
        // TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        // throw new RuntimeException(e);
        // }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t---发出通知");
            }
        }, "t2").start();
    }

}
