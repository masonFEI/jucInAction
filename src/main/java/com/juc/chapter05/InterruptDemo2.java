/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter05;

import java.util.concurrent.TimeUnit;

/**
 * InterruptDemo2
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-03 21:47
 */
public class InterruptDemo2 {

    public static void main(String[] args) {
        // 实例方法interrupt() 仅仅是设置线程的中断状态位为true，不会停止线程
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 300; i++) {
                System.out.println("------:" + i);
            }
            System.out.println("t1线程调用interrupt后的中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("t1线程默认的中断标识：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1.interrupt();// true

        System.out.println("t1线程调用interrupt后的中断标识01：" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("t1线程调用interrupt后的中断标识03：" + Thread.currentThread().isInterrupted());// false,中断不活动的线程不会产生任何影响

    }

}
