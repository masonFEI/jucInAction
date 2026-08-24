/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ThreadDemo3
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-24 19:17
 */

// 第一步，创建资源类
class ShareResource {

    // 定义标志位
    private int       flag = 1;                  // 1 AA 2 BB 3 CC

    // 创建Lock锁
    private Lock      lock = new ReentrantLock();

    // 创建三个condition
    private Condition c1   = lock.newCondition();
    private Condition c2   = lock.newCondition();
    private Condition c3   = lock.newCondition();

    // 打印5次，参数第几轮
    public void print5(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 第一层while判断：必须使用while，防止虚假唤醒
            while (flag != 1) {
                c1.await();
            }

            // 第二层业务代码
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数：" + loop);
            }

            // 第三步：通知
            flag = 2;// 修改标志位的值位为2
            c2.signal();
        } finally {
            lock.unlock();
        }
    }

    // 打印10轮，参数第几轮
    public void print10(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 第一层while判断：必须使用while，防止虚假唤醒
            while (flag != 2) {
                c2.await();
            }

            // 第二层业务代码
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数：" + loop);
            }

            // 第三步：通知
            flag = 3;// 修改标志位的值位为3
            c3.signal();// 通知CC线程
        } finally {
            lock.unlock();
        }
    }

    // 打印15轮，参数第几轮
    public void print15(int loop) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            // 第一层while判断：必须使用while，防止虚假唤醒
            while (flag != 3) {
                c3.await();
            }

            // 第二层业务代码
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :轮数：" + loop);
            }

            // 第三步：通知
            flag = 1;// 修改标志位的值位为1
            c1.signal();// 通知AA线程
        } finally {
            lock.unlock();
        }
    }

}

public class ThreadDemo3 {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

    }

}
