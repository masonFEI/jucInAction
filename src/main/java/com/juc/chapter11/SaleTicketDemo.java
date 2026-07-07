/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter11;

/**
 * SaleTicketDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-07-07 19:50
 */
class Ticket {// 资源类，模拟三个售票员卖完50张票
    private int number     = 50;

    Object      lockObject = new Object();

    public void sale() {

        synchronized (lockObject) {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "张票，剩余" + number);
            }
        }
    }
}

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
