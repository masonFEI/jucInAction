/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;


class Book extends Object {

}


/**
 * LockSyncDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-27 22:44
 */
public class LockSyncDemo {

    final Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("---- hello synchronized code block");
        }
    }

    public synchronized void m2() {
        System.out.println("---- hello synchronized m2");
    }

    public static synchronized void m3() {
        System.out.println("---- hello synchronized m3");
    }

    public static void main(String[] args) {

    }

}
