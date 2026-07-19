/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter12;

/**
 * LockBigDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-11 12:53
 */
public class LockBigDemo {

    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("1111");
            }
            synchronized (objectLock) {
                System.out.println("2222");
            }
            synchronized (objectLock) {
                System.out.println("3333");
            }
            synchronized (objectLock) {
                System.out.println("4444");
            }

            // 前后相连的4个锁，相当于将4个锁合并
            // 加粗加大范围，一次申请锁使用即可，避免次次申请和释放锁，提升了性能

        }, "t1").start();

    }

}
