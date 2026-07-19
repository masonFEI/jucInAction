/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter07;

/**
 * DCLDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-19 19:49
 */
public class DCLDemo {

    // 通过volatile声明，实现线程安全的延迟初始化
    private volatile static DCLDemo instance;

    /**
     *
     * new DCLDemo() 分为三步：
     * 1.分配内存
     * 2.初始化对象
     * 3.赋值给 instance
     * 
     * JIT/CPU 可能重排为 1→3→2：
     * 线程 1 执行 1、3 后，线程 2 发现 instance != null，直接返回未初始化完成的对象，导致崩溃。
     * <p>
     * 所以，需要给 instance 增加volatile，禁止重排
     *
     * @return
     */
    public static DCLDemo getInstance() {
        if (instance == null) {
            synchronized (DCLDemo.class) {
                if (instance == null) {
                    // 隐患：多线程环境下，由于重排序，该对象可能还未完成初始化就被其他线程读取
                    instance = new DCLDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

    }

}
