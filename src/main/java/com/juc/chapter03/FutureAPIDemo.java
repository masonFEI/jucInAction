/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureAPIDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-13 22:05
 */
public class FutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println(Thread.currentThread().getName() + " \t -----come in");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task over";
        });
        new Thread(futureTask, "t1").start();


//        System.out.println(futureTask.get());

        System.out.println(Thread.currentThread().getName() + "\t ----- 忙其他任务了");

//        System.out.println(futureTask.get(3, TimeUnit.SECONDS));

        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("正在处理中");
            }
        }

    }

    /**
     * 1.get容易导致阻塞，一般建议放在程序后面
     * 2.假如我不愿意等待很长时间，我希望过时不候，可以自动离开
     */

}
