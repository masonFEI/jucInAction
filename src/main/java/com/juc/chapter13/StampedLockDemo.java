/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock = ReentrantReadWriteLock +读的过程中也允许获取写锁接入
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-28 22:30
 */
public class StampedLockDemo {

    static int         number      = 37;

    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t" + "写线程结束修改");
    }

    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t" + "come in readLock code block,4 seconds continue...");

        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "\t" + "正在读取中...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t" + "获得成员变量值result:" + result);
            System.out.println("写线程没有修改成功，读锁时候写锁无法接入，传统的读写互斥");
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public static void main(String[] args) {

        StampedLockDemo resource = new StampedLockDemo();

        new Thread(() -> {
            resource.read();
        }, "readThread").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "come in ");
            resource.write();
        }, "writeThread").start();

    }

}
