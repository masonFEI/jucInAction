/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter11;

import org.openjdk.jol.info.ClassLayout;

/**
 * JOLDemo,查看对象的对齐填充
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-04 21:19
 */
public class JOLDemo {

    public static void main(String[] args) {
        // Object o = new Object();
        Object c1 = new Customer();
        System.out.println(ClassLayout.parseInstance(c1).toPrintable());
    }

}

class Customer {
    // 1.第一种，只有对象头，没有其他任何实例数据

    // 2.第二种情况，int+boolean
    int     id;
    boolean flag = false;
}
