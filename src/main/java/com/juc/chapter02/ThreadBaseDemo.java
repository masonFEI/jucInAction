/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter02;

/**
 * 线程启动 demo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-08 23:15
 */
public class ThreadBaseDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
        }, "t1");

        t1.start();
    }

}
