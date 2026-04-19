/**
 * LY.com Inc.
 * Copyright (c) 2004-2026 All Rights Reserved.
 */
package com.juc.chapter03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CompletableFutureMallDemo
 *
 * @author feixuanyu
 * @version 1.0.0
 * @since 2026-04-19 10:23
 */
public class CompletableFutureMallDemo {

    static List<NetMall> list = Arrays.asList(new NetMall("jd"), new NetMall("dangdang"), new NetMall("taobao"));

    /**
     * step by step,一家家搜查
     *
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> String.format("%s in %s price is %.2f", productName, netMall.getNetMallName(), netMall.calcPrice(productName))).collect(Collectors.toList());
    }


    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        List<CompletableFuture<String>> futures = list.stream().map(netMall ->
                CompletableFuture.supplyAsync(() ->
                        String.format("%s in %s price is %.2f", productName, netMall.getNetMallName(), netMall.calcPrice(productName))
                )
        ).collect(Collectors.toList());

        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 链式写法，配合注解 @Accessors(chain = true)
//        Student student = new Student();
//        student.setId(12).setStudentName("114").setMajor("english");

//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//            return "hello 1234";
//        });
////        System.out.println(completableFuture.get());
//        // join与get的区别在于，join在编译时不报出检查异常
//        System.out.println(completableFuture.join());

        System.out.println(ThreadLocalRandom.current().nextDouble() * 2 + "mysql".charAt(0));

        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime - startTime) + " ms");


        System.out.println("-----------------------------------------------");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }

        long endTime2 = System.currentTimeMillis();
        System.out.println("---costTime:" + (endTime2 - startTime2) + " ms");

    }
}


class NetMall {

    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
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
