/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReEntryLockDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-01 23:18
 */
public class ReEntryLockDemo {


    static Lock lock = new ReentrantLock();


    /**
     * 可重入锁-同步方法
     */
    public synchronized void m1() {
        // 指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁，这样的锁就叫做可重入锁
        System.out.println(Thread.currentThread().getName() + "\t ---- m1 come in");
        m2();

        System.out.println(Thread.currentThread().getName() + "\t ---- end");
    }

    public synchronized void m2() {
        System.out.println(Thread.currentThread().getName() + "\t ---- m2 come in");
        m3();
    }

    public synchronized void m3() {
        System.out.println(Thread.currentThread().getName() + "\t ---- m3 come in");
    }

    public static void main(String[] args) {
//        ReEntryLockDemo reEntryLockDemo = new ReEntryLockDemo();
//
//        new Thread(() -> {
//            reEntryLockDemo.m1();
//        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "\t ---come in");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();

    }

    /**
     * 可重入锁-同步代码块
     *
     */
    private static void reEntryM1() {
        final Object object = new Object();

        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t ----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t ----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t ----内层调用");
                    }
                }
            }
        }, "t1").start();
    }


}
