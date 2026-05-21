/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicIntegerDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-21 22:12
 */
class MyNumber {

    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }
}

public class AtomicIntegerDemo {

    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {

        MyNumber myNumber = new MyNumber();

        // 50个线程
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {

                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }

            }, String.valueOf(i)).start();
        }

        // 等待上面计算完成
        // try {
        // TimeUnit.SECONDS.sleep(2);
        // } catch (InterruptedException e) {
        // throw new RuntimeException(e);
        // }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t" + "result:" + myNumber.atomicInteger.get());

    }

}
