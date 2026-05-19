/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter08;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * T3
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-19 20:07
 */
public class T3 {

    private volatile int number;

    public int getNumber() {
        return number;
    }

    public synchronized void setNumber() {
        number++;
    }


    // -------------------------- 原子整型类 -------------------

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger() {
        return atomicInteger.get();
    }

    public void setAtomicInteger() {
        atomicInteger.getAndIncrement();
    }


}
