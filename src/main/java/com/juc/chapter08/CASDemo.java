/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter08;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CASDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-19 23:29
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2026) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2026) + "\t" + atomicInteger.get());

    }

}
