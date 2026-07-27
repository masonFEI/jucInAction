/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter13;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLockDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-27 20:16
 */

class MyResource {// 资源类，模拟一个简单的缓存

    Map<String, String> map    = new HashMap<>();

    // ====== ReentrantLock 等价于synchronized，独占锁
    Lock                lock   = new ReentrantLock();

    // ====== ReentrantReadWriteLock 一体两面，读写互斥，读读共享
    ReadWriteLock       rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        // lock.lock();
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在写入");
            map.put(key, value);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "完成写入");
        } finally {
            // lock.unlock();
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
        // lock.lock();
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取");
            String result = map.get(key);
            try {
                // TimeUnit.MILLISECONDS.sleep(200);
                // 暂停2000ms,演示读锁没有完成之前，写锁无法获得
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "完成读取" + "\t" + result);
        } finally {
            // lock.unlock();
            rwLock.readLock().unlock();
        }
    }

}

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {

        MyResource myResource = new MyResource();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 验证1秒钟后，三个写线程不能先于读锁释放前拿到写锁
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, "新写锁" + String.valueOf(i)).start();
        }

    }

}
