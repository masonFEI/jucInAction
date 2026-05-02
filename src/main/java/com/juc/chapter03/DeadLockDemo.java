/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

/**
 * DeadLockDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-02 11:11
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "持有objectA锁，尝试获取objectB锁");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "持有objectA锁，获取objectB锁成功");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "持有objectB锁，尝试获取objectA锁");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "持有objectB锁，获取objectA锁成功");
                }
            }
        }, "B").start();

    }

}
