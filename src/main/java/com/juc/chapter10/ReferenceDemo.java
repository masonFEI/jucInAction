/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter10;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

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
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("--------------gc before 内存够用: " + weakReference.get());
        System.gc();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("gc after 内存够用" + weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.out.println("--------------softReference: " + softReference.get());
        System.gc();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("gc after 内存够用" + softReference.get());
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();

        System.out.println("gc before" + myObject);

        myObject = null;

        System.gc();// 人工开启gc，一般不用
        System.out.println("gc after" + myObject);
    }

}
