/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter11;

import org.openjdk.jol.info.ClassLayout;

/**
 * 锁升级demo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-06 23:30
 */
public class SynchronizedUpDemo {

    public static void main(String[] args) {
        // -XX:-UseBiasedLocking (关闭偏向锁)
        Object o = new Object();
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 偏向锁
     */
    private static void biasedLock() {
        // biased lock
        // -XX:+UseBiasedLocking (开启偏向锁)
        // -XX:-UseBiasedLocking (关闭偏向锁)
        // -XX:BiasedLockingStartupDelay=0 （关闭延迟，演示偏向锁时需要开启）
        Object o = new Object();
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 无锁
     */
    private static void noLock() {
        // 无锁（001）(value编码倒着看)
        Object o = new Object();

        System.out.println("10进制：" + o.hashCode());
        System.out.println("16进制：" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制：" + Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

}
