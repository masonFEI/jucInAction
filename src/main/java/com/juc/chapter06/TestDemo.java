/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter06;

/**
 * TestDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-08 23:58
 */
public class TestDemo {


    private volatile int value = 0;

    public int getValue() {
        return value; // 利用volatile保证读取操作的可见性
    }

    public synchronized int setValue() {
        return ++value;// 利用 synchronized 保证复合操作的原子性
    }


}
