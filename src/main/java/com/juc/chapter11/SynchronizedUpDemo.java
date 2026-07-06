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
        // 无锁（001）(value编码倒着看)
        Object o = new Object();

        System.out.println("10进制：" + o.hashCode());
        System.out.println("16进制：" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制：" + Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

}
