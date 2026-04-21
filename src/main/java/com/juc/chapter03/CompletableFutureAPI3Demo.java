/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFutureAPI3Demo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-21 23:12
 */
public class CompletableFutureAPI3Demo {

    public static void main(String[] args) {

        // 任务A执行完执行B,B不需要A的结果
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());

        // 任务A执行完执行B,B需要A的结果，但是任务B无返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(System.out::println).join());

        // 任务A执行完执行B,B需要A的结果，同时任务B有返回值
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());

    }


    private void thenAcceptDemo() {
        CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f + 2;
        }).thenApply(f -> {
            return f + 3;
        }).thenAccept(System.out::println);
    }

}
