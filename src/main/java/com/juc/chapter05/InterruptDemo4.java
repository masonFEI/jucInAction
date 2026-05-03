/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter05;

/**
 * InterruptDemo4
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-03 22:31
 */
public class InterruptDemo4 {

    public static void main(String[] args) {
        // 测试当前线程是否被中断（检查中断标志），返回一个boolean并清除中断状态
        // 第二次再调用时中断状态已经被清除，将返回一个false
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("----1");
        Thread.currentThread().interrupt();//中断标志位设置为true
        System.out.println("----2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());



        Thread.interrupted();// 静态方法,入参ClearInterrupted(是否重置中断标识)为true
        Thread.currentThread().isInterrupted();// 实例方法
    }

}
