/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.sync;

/**
 * ThreadDemo1
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-20 22:42
 */
// 第一步 创建资源类，定义属性和操作方法

class Share {
    // 初始值
    private int number = 0;

    // 加一
    public synchronized void incr() throws InterruptedException {
        // 第二步 判断 干活 通知
    }

    // 减一
    public synchronized void decr() throws InterruptedException {
        // 判断
    }

}

public class ThreadDemo1 {
}
