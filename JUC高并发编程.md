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
4. 他们都可以被interrupted方法中断

# 02-Lock接口

Thread的start方法调用后，不一定立马创建线程，需要CPU执行后创建线程

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

ConcurrentHashMap解决HashMap线程安全问题，采用分段锁机制

# 06-多线程锁

见chapter04/Lock8Demo，注释中即为8种加锁demo场景

## 非公平锁与公平锁、可重入锁、死锁

# 07-Callable接口

Callable创建线程：通过FutureTask包装Callable对象，作为Thread的target

相比于Runnable，Callable，可以有返回值，可以抛出异常

## FutureTask（未来任务）

需要异步执行的任务;FutureTask第二次调用get方法直接返回结果，不会再执行中间步骤了

# 08-JUC强大的辅助类

1. CountDownLatch（倒计时锁）: 用于控制线程执行顺序；主要方法有`countDown()`和`await()`
2. CyclicBarrier（循环栅栏）: 让一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障才继续执行
3. Semaphore（信号量）: 用于控制同时访问特定资源的线程数量，模拟车库、限流等场景

# 09-ReentrantReadWriteLock

读锁：共享锁； 写锁：独占锁

锁降级：写锁降级为读锁

# 10-BlockingQueue阻塞队列