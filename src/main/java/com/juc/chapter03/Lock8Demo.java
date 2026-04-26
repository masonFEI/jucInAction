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

    public static synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }

    public void hello() {
        System.out.println("-----hello");
    }
}

/**
 * 谈谈你对多线程锁的理解，8锁案例说明
 * 口诀: 线程 操作 资源类
 * <p>
 * 1. 标准访问有ab两个线程，先打印邮件还是短信? sendEmail
 * 2. sendEmail 方法中加入暂停3秒钟，请问先打印邮件还是短信？sendEmail
 * 3. 添加一个普通的hello方法，请问先打印邮件还是hello？hello
 * 4. 有两部手机，请问先打印邮件还是短信？sendSMS
 * 5. 有两个静态同步方法，有1部手机，请问先打印邮件还是短信？sendEmail
 * 6. 有两个静态同步方法，有2部手机，请问先打印邮件还是短信？sendEmail
 * 7. 有1个静态同步方法，有1个普通同步方法，有1部手机，请问先打印邮件还是短信？sendSMS
 * 8. 有1个静态同步方法，有1个普通同步方法，有2部手机，请问先打印邮件还是短信？sendSMS
 *
 */
public class Lock8Demo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

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
//            phone.sendSMS();
//            phone.hello();
            phone2.sendSMS();
        }, "b").start();

    }

}
