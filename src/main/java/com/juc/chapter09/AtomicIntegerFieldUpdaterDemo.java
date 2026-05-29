/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * AtomicIntegerFieldUpdaterDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-23 22:13
 */

class BankAccount {
    // 资源类
    String              bankName = "CCB";

    // 1.更新的对象属性必需使用public volatile修饰符
    public volatile int money    = 0;    // 钱数

    public void add() {
        money++;
    }

    // 2.因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
    // 使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性
    static final AtomicIntegerFieldUpdater<BankAccount> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    // 不加synchronized，保证高性能原子性，局部微创小手术
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }

}

/**
 * 以一种线程安全的方式操作非线程安全对象的某些字段
 * 需求：
 * 10个线程，
 * 每个线程转账1000，
 * 不使用synchronized,尝试使用AtomicIntegerFieldUpdater来实现线程安全的转账操作
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();

        // 线程的计数器，完成一个减一个
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        // bankAccount.add();
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t " + "result:" + bankAccount.money);
    }

}
