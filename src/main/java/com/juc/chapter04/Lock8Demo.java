/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter04;

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
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

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
 * <p>
 * <p>
 * 笔记总结：
 * 1-2：
 * 一个对象里面如果有多个synchronized方法，某一时刻内，只要一个线程去调用其中的一个synchronized方法了，其他线程只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这个对象的synchronized方法。
 * 锁的是当前对象this，被锁定后，其他线程都不能进入到当前对象的其他synchronized方法。
 * <p>
 * 3：
 * 普通方法和同步锁无关，所以先打印hello
 * <p>
 * 4：
 * 换成两个对象后，不是同一把锁了，情况立刻变化了，先打印短信
 * <p>
 * 5-6：
 * 加个static后，锁的不是当前对象了，而是类对象了，所有的对象的这个静态同步方法用的都是同一把锁了，所以先打印邮件
 * <p>
 * 7-8：
 * 静态同步方法与普通同步方法之间不存在竞态条件的。
 * 一个是静态同步方法，一个是普通同步方法，锁的也是不同的对象了，所以先打印短信
 */
public class Lock8Demo {

    public static void main(String[] args) {
        Phone phone = new Phone();
//        Phone phone2 = new Phone();

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
//            phone.hello();
//            phone2.sendSMS();
        }, "b").start();

    }

}
