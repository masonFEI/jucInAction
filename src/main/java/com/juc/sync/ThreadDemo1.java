/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.sync;

/**
 * ThreadDemo1
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-20 22:42
 */

// 第一步 创建资源类，定义属性和操作方法
class Share {
    // 初始值
    private int number = 0;

    // 因为wait方法存在虚假唤醒可能，因此wait方法应始终在循环中使用

    // 加一
    public synchronized void incr() throws InterruptedException {
        // 第二步 判断 干活 通知
//        if (number != 0) {// 判断number是否为0，如果不是0，等待
//            this.wait();// 在哪里睡，就在哪里醒
//        }

        while (number!=0) {
            this.wait();
        }
        
        
        // 如果number值是0，就+1操作
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);

        // 通知其他线程
        this.notifyAll();

    }

    // 减一
    public synchronized void decr() throws InterruptedException {
        // 第二步 判断 干活 通知
        // if (number != 1) {// 判断number是否为1，如果不是1，等待
        // this.wait();
        // }

        while (number!=1) {
            this.wait();
        }
        
        
        // 如果number值是1，就-1操作
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);

        // 通知其他线程
        this.notifyAll();
    }

}

public class ThreadDemo1 {

    public static void main(String[] args) {
        // 第三步，创建多个线程，调用资源类的操作方法
        Share share = new Share();

        // 创建两个线程
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.incr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    share.decr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }

}
