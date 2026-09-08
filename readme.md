# JUC 并发编程学习笔记

本项目整合了三套 Java `java.util.concurrent` (JUC) 并发编程视频课程的学习笔记：

- **尚硅谷 · 周阳老师《JUC 并发编程与源码分析》** —— 见 [`笔记.md`](./笔记.md)（15 章）
- **马士兵教育《Java 线程池源码深度解析：ThreadPoolExecutor》** —— 见 [`java线程池源码深度解析.md`](./java线程池源码深度解析.md)
- **尚硅谷《搞定 JUC：juc 并发编程》**（对标阿里 P6-P7）—— 见 [`JUC高并发编程.md`](./JUC高并发编程.md)

笔记按章节记录核心知识点，对应的 Java Demo 代码位于 `src/main/java/com/juc/` 下，涵盖 CompletableFuture、Java锁、
LockSupport、JMM、volatile、CAS、原子操作类、 ThreadLocal、AQS、ReentrantReadWriteLock、StampedLock、BlockingQueue、ThreadPool
等内容，可结合笔记一边阅读一边跑代码加深理解。