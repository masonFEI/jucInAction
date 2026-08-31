/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutorDemo
 * <p>
 * 演示 {@link ThreadPoolExecutor} 的五步工作流程（见 JUC高并发编程.md 行 117-118）：
 * <pre>
 *   提交任务 → 核心线程数未满则创建核心线程
 *          → 核心线程满了进入工作队列
 *          → 队列满了则创建非核心线程（直到达到最大线程数）
 *          → 仍超出则执行拒绝策略
 * </pre>
 * <p>
 * 七大参数构造：corePoolSize=2, maximumPoolSize=5, keepAliveTime=2s,
 * 队列容量=3, 默认线程工厂, 默认 AbortPolicy。
 * <p>
 * 提交 9 个任务可观察完整五步：
 * <ul>
 *   <li>第 1-2 个 → 落到核心线程 pool-1-thread-1 / pool-1-thread-2</li>
 *   <li>第 3-5 个 → 进入 ArrayBlockingQueue（容量 3）</li>
 *   <li>第 6-8 个 → 触发非核心线程 pool-1-thread-3 / pool-1-thread-4 / pool-1-thread-5（core 已满 + queue 已满）</li>
 *   <li>第 9 个 → core(2) + queue(3) + non-core(3) = 8 已满，触发 AbortPolicy 抛出 RejectedExecutionException</li>
 * </ul>
 * <p>
 * 对比 {@link FutureThreadPoolDemo}：该文件只用 {@code Executors.newFixedThreadPool(3)}
 * 的便捷工厂，无法暴露七大参数。
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-08-31
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        // ============ 第①步：构建 7 参线程池 ============
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                /* corePoolSize    */ 2,
                /* maximumPoolSize */ 5,
                /* keepAliveTime   */ 2L,
                /* unit            */ TimeUnit.SECONDS,
                /* workQueue       */ new ArrayBlockingQueue<>(3),
                /* threadFactory   */ Executors.defaultThreadFactory(),
                /* handler         */ new ThreadPoolExecutor.AbortPolicy());

        System.out.println("=== 场景一：默认 AbortPolicy，提交 9 个任务 ===");
        try {
            for (int i = 1; i <= 9; i++) {
                final int taskNo = i;
                executor.execute(() -> printTask(taskNo));
                System.out.println("--- 提交 task-" + taskNo
                        + " 后，poolSize=" + executor.getPoolSize()
                        + ", queueSize=" + executor.getQueue().size());
            }
        } catch (RejectedExecutionException e) {
            System.out.println("[拒绝策略触发] " + e.getClass().getSimpleName()
                    + "：当前线程 " + Thread.currentThread().getName()
                    + " 提交的任务被拒绝。原因：" + e.getMessage());
        } finally {
            executor.shutdown();
        }

        // ============ 切换拒绝策略为 CallerRunsPolicy，再跑一次 ============
        System.out.println();
        System.out.println("=== 场景二：切到 CallerRunsPolicy，多余任务由 main 线程执行 ===");
        ThreadPoolExecutor executor2 = new ThreadPoolExecutor(
                2, 5, 2L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        try {
            for (int i = 1; i <= 9; i++) {
                final int taskNo = i;
                executor2.execute(() -> printTask(taskNo));
                System.out.println("--- 提交 task-" + taskNo
                        + " 后，poolSize=" + executor2.getPoolSize()
                        + ", queueSize=" + executor2.getQueue().size());
            }
            // 让 worker 有机会把所有任务消费完，便于观察 CallerRunsPolicy 效果
            executor2.shutdown();
            executor2.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor2.shutdownNow();
        }

        // ============ 简单演示另外两种策略 ============
        System.out.println();
        System.out.println("=== 场景三：DiscardPolicy 直接丢弃（不抛异常）===");
        runWithPolicy("DiscardPolicy", new ThreadPoolExecutor.DiscardPolicy());

        System.out.println();
        System.out.println("=== 场景四：DiscardOldestPolicy 丢弃队列头部最旧任务 ===");
        runWithPolicy("DiscardOldestPolicy", new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    private static void runWithPolicy(String policyName, RejectedExecutionHandler handler) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 3, 2L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
                handler);
        for (int i = 1; i <= 6; i++) {
            final int taskNo = i;
            try {
                executor.execute(() -> printTask(taskNo));
                System.out.println("--- [" + policyName + "] 提交 task-" + taskNo
                        + " 后，poolSize=" + executor.getPoolSize()
                        + ", queueSize=" + executor.getQueue().size());
            } catch (RejectedExecutionException e) {
                System.out.println("[" + policyName + "] task-" + taskNo + " 被拒绝");
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void printTask(int taskNo) {
        String threadName = Thread.currentThread().getName();
        System.out.println(">>> task-" + taskNo + " 正在执行，线程=" + threadName);
        try {
            // 故意停留一小段时间，便于线程池状态稳定
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
