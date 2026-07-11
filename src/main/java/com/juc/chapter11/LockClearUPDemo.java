/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter11;

/**
 * LockClearUPDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-11 12:44
 */
public class LockClearUPDemo {

    static Object objectLock = new Object();

    public void m1() {
        // synchronized (objectLock) {
        // System.out.println("------ hello LockClearUPDemo m1 ------");
        // }

        // 锁消除问题，JIT编译器会无视他， synchronized (o)，每次new出来的，不存在锁了，非正常的
        Object o = new Object();
        synchronized (o) {
            System.out.println("------ hello LockClearUPDemo m1 ------" + "\t" + o.hashCode() + "\t" + objectLock.hashCode());
        }

    }

    public static void main(String[] args) {
        LockClearUPDemo lockClearUPDemo = new LockClearUPDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(lockClearUPDemo::m1, String.valueOf(i)).start();
        }

    }

}
