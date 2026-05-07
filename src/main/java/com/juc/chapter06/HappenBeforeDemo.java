/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter06;

/**
 * HappenBeforeDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-07 09:03
 */
public class HappenBeforeDemo {

    static Object objectLock = new Object();

    public static void main(String[] args) {

        // 对于同一把锁objectLock，threadA一定先unlock同一把锁后threadB才能lock同一把锁，A先行发生于B
        synchronized (objectLock) {

        }

    }

}
