/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * AccumulatorCompareDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-24 22:37
 */
class ClickNumber {// 资源类
    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }

}

public class AccumulatorCompareDemo {

    public static final int _1W          = 10000;
    public static final int threadNumber = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch3 = new CountDownLatch(threadNumber);
        CountDownLatch countDownLatch4 = new CountDownLatch(threadNumber);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " ms" + "\t clickBySynchronized:" + clickNumber.number);

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " ms" + "\t clickByAtomicLong:" + clickNumber.atomicLong.get());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " ms" + "\t clickByLongAdder:" + clickNumber.longAdder.sum());

        startTime = System.currentTimeMillis();
        for (int i = 0; i < threadNumber; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100 * _1W; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            }, String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " ms" + "\t clickByLongAccumulator:" + clickNumber.longAccumulator.get());


        new HashMap<>().put(1,1);

    }

}
