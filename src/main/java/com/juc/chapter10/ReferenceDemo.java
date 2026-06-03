/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter10;

/**
 * ReferenceDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-06-03 23:28
 */

class MyObject {

    // 这个方法一般不用复写，我们只是为了教学给大家演示案例做说明
    @Override
    protected void finalize() throws Throwable {
        System.out.println(" ------ invoke finalize method~!!!");
    }
}

public class ReferenceDemo {

    public static void main(String[] args) {

    }

}
