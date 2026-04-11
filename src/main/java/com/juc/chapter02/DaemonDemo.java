/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter02;

import java.util.concurrent.TimeUnit;

/**
 * DaemonDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-09 22:20
 */
public class DaemonDemo {


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行，" +
                    (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));

            while (true) {

            }

        }, "t1");

//        t1.setDaemon(true); 必须在start()之前设置，否则报错：java.lang.IllegalThreadStateException
        t1.start();
//        t1.setDaemon(true);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t --- end 主线程");
    }

}
