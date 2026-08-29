/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueueDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-29 19:35
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        // 创建阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第一组
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.element());

        // 第三组
        // System.out.println(blockingQueue.add("d"));
        try {
            // 当阻塞队列满时，再往队列里面添加元素，put方法会一直阻塞直到队列有空间
            blockingQueue.put("d");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
