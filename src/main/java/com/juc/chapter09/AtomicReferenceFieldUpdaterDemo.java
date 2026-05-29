/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

class MyVar {
    public volatile Boolean                                  isInit                = false;

    static final AtomicReferenceFieldUpdater<MyVar, Boolean> referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class,
        "isInit");

    public void init(MyVar myVar) {
        if (referenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE)) {
            System.out.println(Thread.currentThread().getName() + "\t" + "---- start init,need 3 seconds");

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + "\t" + "---- over init");
        } else {
            System.out.println(Thread.currentThread().getName() + "\t" + "---- 已经有线程在进行初始化工作了");
        }

    }
}

/**
 * 多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，
 * 要求只能被初始化一次，只有一个线程操作成功。
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-23 22:44
 */
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {
        MyVar myVar = new MyVar();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myVar.init(myVar);
            }, String.valueOf(i)).start();
        }

    }

}
