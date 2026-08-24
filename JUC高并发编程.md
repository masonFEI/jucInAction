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

