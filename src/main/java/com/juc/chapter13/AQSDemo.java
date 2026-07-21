/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter13;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQSDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-21 19:58
 */
public class AQSDemo {

    public static void main(String[] args) {

        // 内部 sync 继承自AQS
        new ReentrantLock();

        // 内部 sync 继承自AQS
        new CountDownLatch(10);

        // 内部 sync 继承自AQS
        new Semaphore(10);
    }

}
