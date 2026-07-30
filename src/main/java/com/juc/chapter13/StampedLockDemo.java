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

    // 悲观读，读没有完成时，写锁无法获得锁
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

    // 乐观读，读的过程中也允许获取写锁介入
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        // 故意间隔4秒钟，很乐观认为读取中没有其他线程修改过number值，具体靠bool判断
        System.out.println("4秒前stampedLock.validate方法值（true无修改，false有修改）" + "\t" + stampedLock.validate(stamp));

        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "\t" + "正在读取... " + i + " 秒后stampedLock.validate方法值（true无修改，false有修改）" + "\t"
                                   + stampedLock.validate(stamp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改----有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result: " + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

        System.out.println(Thread.currentThread().getName() + "\t" + " finally value: " + result);
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(() -> {
            resource.tryOptimisticRead();
        }, "readThread").start();

        // 暂停2秒钟，读过程可以写接入
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "come in ");
            resource.write();
        }, "writeThread").start();

    }

    /**
     * 传统版
     */
    public void classic() {
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
