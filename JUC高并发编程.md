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