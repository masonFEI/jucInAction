/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.TimeUnit;

/**
 * Lock8Demo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-25 23:20
 */

class Phone {// 资源类

    public synchronized void sendEmail() {
        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }
}

/**
 * 谈谈你对多线程锁的理解，8锁案例说明
 * 口诀: 线程 操作 资源类
 * <p>
 * 1 标准访问有ab两个线程，先打印邮件还是短信? sendEmail
 *
 */
public class Lock8Demo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();

        // 暂停毫秒，保证a线程先启动
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            phone.sendSMS();
        }, "b").start();

    }

}
