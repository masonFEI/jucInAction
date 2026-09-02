# 01-JUC概述

## 线程的状态

- New (新建)
- Runnable (准备就绪)
- Blocked (阻塞)
- Waiting (不见不散)
- Timed_Waiting (过时不候)
- Terminated (终结)

## wait/sleep

1. sleep是Thread的静态方法，wait是Object的方法，任何对象实例都能调用
2. sleep不会释放锁（它也不需要占用锁），wait会释放锁
3. sleep可以在任何地方使用，wait要在同步代码块或同步方法中使用
4. 它们都可以响应 `interrupt()` 调用，在阻塞中抛出 InterruptedException

# 02-Lock接口

Thread的start方法调用后，线程立即进入新建状态，由 JVM 在合适的时机创建对应的原生线程调度执行 run() 方法，进入就绪（Runnable）状态等待 CPU 调度。

- Lock可以让等待锁的线程响应中断，而synchronized不可以。 使用synchronized时，等待的线程会一直等待下去，不能够响应中断。

- 通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。

# 03-线程间的通信

存在一个共享变量，线程a对变量+1，线程b对变量-1（实际上是实现两个线程交替执行）
`wait与notify`

因为wait方法存在虚假唤醒可能，因此wait方法应始终在循环中使用

见lock/ThreadDemo2

# 04-线程间的定制通信

见lock/ThreadDemo3

# 05-集合的线程安全

## ArrayList线程不安全

CopyOnWriteArrayList （JUC中）解决ArrayList的线程安全问题;

- 写时复制技术，在修改时先复制一份数组，在副本上进行修改，修改完将引用指向新数组
- 支持并发读

## HashSet线程不安全

CopyOnWriteArraySet解决对应的线程安全问题,内部是CopyOnWriteArrayList

## HashMap线程不安全

ConcurrentHashMap解决HashMap线程安全问题，JDK 8 起采用 CAS + 单个桶粒度的 synchronized + 红黑树（高冲突时链表退化为树）实现；JDK 7 及以前才使用 Segment 分段锁机制。

# 06-多线程锁

见chapter04/Lock8Demo，注释中即为8种加锁demo场景

## 非公平锁与公平锁、可重入锁、死锁

# 07-Callable接口

Callable创建线程：通过FutureTask包装Callable对象，作为Thread的target

相比于Runnable，Callable可以有返回值，可以抛出异常

## FutureTask（未来任务）

需要异步执行的任务;FutureTask第二次调用get方法直接返回结果，不会再执行中间步骤了

# 08-JUC强大的辅助类

1. CountDownLatch（倒计时门闩）: 让一个或多个线程等待其他线程完成一组操作后继续执行；主要方法有`countDown()`和`await()`
2. CyclicBarrier（循环栅栏）: 让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障才继续执行
3. Semaphore（信号量）: 用于控制同时访问特定资源的线程数量，模拟车库、限流等场景

# 09-ReentrantReadWriteLock

读锁：共享锁； 写锁：独占锁

锁降级：写锁降级为读锁

# 10-BlockingQueue阻塞队列

# 11-ThreadPool线程池

## 线程池的分类

一池N线程：`Executors.newFixedThreadPool(n)`：固定大小的线程池

一池一线程：`Executors.newSingleThreadExecutor()`：只有一个线程的线程池

可扩容线程池：`Executors.newCachedThreadPool()`：根据需要创建新线程，空闲线程会被回收

线程池不允许使用`Executors`去创建，而是通过`ThreadPoolExecutor`的方式来创建

## 线程池的创建（推荐方式）

使用`ThreadPoolExecutor`自定义创建，

七大参数：

- corePoolSize：核心线程数
- maximumPoolSize：最大线程数
- keepAliveTime：空闲线程存活时间
- unit：时间单位
- workQueue：任务等待队列
- threadFactory：线程工厂,用于创建线程
- handler：拒绝策略，包括四种内置拒绝策略：
    - `AbortPolicy`：默认策略，抛出 `RejectedExecutionException` 异常
    - `CallerRunsPolicy`：由调用线程（提交任务的线程）执行该任务
    - `DiscardPolicy`：直接丢弃任务，不抛异常
    - `DiscardOldestPolicy`：丢弃队列中最旧的任务，然后重新提交当前任务

工作流程：提交任务 → 核心线程数未满则创建核心线程 → 核心线程满了进入工作队列 → 队列满了则创建非核心线程（直到达到最大线程数）→
仍超出则执行拒绝策略

# 12 Fork/join

fork将任务拆分；join将任务合并
