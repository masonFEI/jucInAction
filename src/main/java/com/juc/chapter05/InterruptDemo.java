/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * InterruptDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-02 21:34
 */
public class InterruptDemo {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted被修改为true，程序停止");
                    break;
                }

                System.out.println("t1------ hello interrupt");
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();

        // 也可以t1自己设置
//        t1.interrupt();
    }


    private void atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean 被修改为true，程序停止");
                    break;
                }
                System.out.println("t1------ hello atomicBoolean");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private void volatileDemo() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("t1------ hello volatile");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }


}
