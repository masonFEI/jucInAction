/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter09;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * LongAdderAPIDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-05-24 22:24
 */
public class LongAdderAPIDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println("sum: " + longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println(longAccumulator.get());

        LongAccumulator longAccumulator1 = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left * right;
            }
        }, 1);

        longAccumulator1.accumulate(2);
        longAccumulator1.accumulate(3);
        System.out.println(longAccumulator1.get());

    }

}
