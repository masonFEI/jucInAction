/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFutureMallDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-19 10:23
 */
public class CompletableFutureMallDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 链式写法，配合注解 @Accessors(chain = true)
//        Student student = new Student();
//        student.setId(12).setStudentName("114").setMajor("english");


        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 1234";
        });
//        System.out.println(completableFuture.get());
        // join与get的区别在于，join在编译时不报出检查异常
        System.out.println(completableFuture.join());

    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
class Student {
    private Integer id;

    private String studentName;

    private String major;
}
