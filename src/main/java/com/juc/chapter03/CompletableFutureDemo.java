/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * CompletableFutureDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-11 22:17
 */
public class CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        System.out.println(futureTask.get());

    }

}

class MyThread2 implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}

class MyThread implements Callable<String> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    // 有返回，可以抛出异常
    public String call() throws Exception {
        System.out.println("---come in call()");
        return "hello Callable";
    }
}
