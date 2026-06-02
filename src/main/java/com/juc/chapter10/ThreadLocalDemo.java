/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter10;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocalDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-06-01 23:05
 */

class House { // 资源类

    int saleCount = 0;

    public synchronized void saleHouse() {
        saleCount++;
    }

    // 通常使用匿名内部类初始化
    // ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>() {
    // @Override
    // protected Integer initialValue() {
    // return 0;
    // }
    // };

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void saleVolumeByThreadLocal() {
        saleVolume.set(saleVolume.get() + 1);
    }

}

public class ThreadLocalDemo {

    public static void main(String[] args) {
        House house = new House();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;

                // System.out.println(size);
                try {
                    for (int j = 0; j < size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "售出房屋数量: " + house.saleVolume.get());
                } finally {
                    house.saleVolume.remove();
                }
            }).start();
        }

        // 等待所有线程执行完毕
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("总共售出房屋数量: " + house.saleCount);
    }

}
