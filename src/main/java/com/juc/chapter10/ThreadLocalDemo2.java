/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocalDemo2
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-06-02 19:14
 */

class MyData {

    ThreadLocal<Integer> threadLocalField = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocalField.set(1 + threadLocalField.get());
    }

}

public class ThreadLocalDemo2 {

    public static void main(String[] args) {
        MyData myData = new MyData();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {

            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    try {
                        Integer beforeInt = myData.threadLocalField.get();
                        myData.add();
                        Integer afterInt = myData.threadLocalField.get();

                        System.out.println(Thread.currentThread().getName() + "\t" + "before:" + beforeInt + "\t" + afterInt);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        myData.threadLocalField.remove();
                    }
                });
            }

        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }

    }

}
