/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter07;

import java.util.concurrent.TimeUnit;

class MyNumber {
    volatile int number;

    public void addPlusPlus() {
        number++;
    }

}

/**
 * volatile不保证原子性
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-15 09:07
 */
public class VolatileNoAtomicDemo {

    public static void main(String[] args) {

        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(myNumber.number);

    }

}
