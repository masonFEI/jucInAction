/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter07;

import java.util.concurrent.TimeUnit;

/**
 * VolatileSeeDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-14 22:23
 */
public class VolatileSeeDemo {

    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---- come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t ---- flag 被设置为false,程序停止");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成，flag:" + flag);

    }

}
