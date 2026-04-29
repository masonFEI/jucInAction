/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;


import java.util.concurrent.locks.ReentrantLock;

class Ticket {// 资源类，模拟三个售票员卖完50张票
    private int number = 50;

    //    ReentrantLock lock = new ReentrantLock();// 非公平锁
    ReentrantLock lock = new ReentrantLock(true);// 公平锁

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "张票，剩余" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}


/**
 * SaleTicketDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-29 23:31
 */
public class SaleTicketDemo {


    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                // sale
                ticket.sale();
            }
        }, "a").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                // sale
                ticket.sale();
            }
        }, "b").start();

        new Thread(() -> {
            for (int i = 0; i < 55; i++) {
                // sale
                ticket.sale();
            }
        }, "c").start();
    }


}
