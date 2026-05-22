/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * AtomicMarkableReferenceDemo
 *
 * AtomicStampedReference和AtomicMarkableReference的区别：
 * AtomicStampedReference 使用version号区分多次修改
 * AtomicMarkableReference 使用boolean标记区分多次修改，类似一次性筷子
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-22 08:54
 */
public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "默认标识：" + marked);

            try {
                // 等待后面的t2拿到默认标识
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            markableReference.compareAndSet(100, 1000, marked, !marked);
        }, "t1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t" + "默认标识：" + marked);

            try {
                // 等待后面的t2拿到默认标识
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);

            System.out.println(Thread.currentThread().getName() + "\t" + "修改成功否：" + b + "\t" + "当前最新值：" + markableReference.getReference());

        }, "t2").start();
    }

}
