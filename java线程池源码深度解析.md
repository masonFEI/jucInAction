# java线程池执行流程

线程池执行流程如下：

1. **提交任务**：调用 `execute()` 或 `submit()` 方法提交任务。
2. **判断核心线程数**：如果当前运行的线程数小于 `corePoolSize`，则创建新线程执行任务（即使其他线程空闲）。
3. **入队等待**：如果线程数已达到 `corePoolSize`，则将任务放入工作队列（`BlockingQueue`）等待。
4. **判断最大线程数**：如果队列已满，且当前线程数小于 `maximumPoolSize`，则创建非核心线程执行任务。
5. **执行拒绝策略**：如果队列已满且线程数已达到 `maximumPoolSize`，则执行拒绝策略（如抛出异常、丢弃任务等）。
6. **任务执行完毕**：线程执行完任务后，从工作队列中获取下一个任务继续执行（如果队列为空则阻塞等待）。
7. **线程回收**：当线程空闲时间超过 `keepAliveTime`（非核心线程）或当线程池中的线程数量减小时，回收多余的线程（包括非核心线程，甚至在
   `allowCoreThreadTimeOut` 为 true 时回收核心线程）。

## 线程池状态

### 核心字段ctl

ctl表述了两个状态

1. 线程池当前的状态（高三位）
   线程池的5种状态
    1. RUNNING (111)（-1）（正常状态，可以正常接受处理任务，也可以处理队列中的任务）
    2. SHUTDOWN (000)(0)（不接受新任务，已经接受的任务会正常处理完毕，处理工作队列中的任务）
    3. STOP (001)(1)（不接受新任务，中断正在处理任务的线程，不处理工作队列中的任务）
    4. TIDYING (过渡状态) (010)(2)
        - `tryTerminate()`, SHUTDOWN转为TIDYING状态，需要工作线程数为0，工作队列为空
        - `tryTerminate()`, 需要工作线程数为0
    5. TERMINATED (011)(3)（线程池销毁状态）
2. 表示线程池当前的工作线程个数（低29位）

## 线程池addWorker

执行任务的流程，并且做了终端线程相关的lock操作

Worker 类中tryAcquire为非可重入锁;因为在中断时，也需要对worker进行lock,不能获取代表当前工作线程正在执行任务

任务addWork后，线程启动，执行worker的run方法；

## getTask方法

从工作队列中获取任务;
核心线程死等，非核心线程限时等;

## processWorkerExit方法

释放工作线程

## shutdown方法

将线程池的状态置为SHUTDOWN
优雅关闭线程池方法，其中interruptIdleWorkers是关闭空闲的线程（也就是没有持有锁的线程）
至于工作中的线程与处于等待队列中的任务，则都会执行完；
这些shutdown后依旧执行的线程，在getTask拿不到任务后，执行processWorkerExit方法关闭线程;

另外shutdown后的线程池，就算工作中的线程数低于核心线程数，不会再添加新的线程了，因为Work类中的addWorker方法会校验线程池状态

## shutdownNow方法

将线程池的状态置为STOP
工作中的任务也退出中断所有工作线程（interruptWorkers）并清空等待队列（drainQueue）返回未执行的任务列表；
线程池状态为STOP后，即使核心线程在getTask中也会被中断，最终执行processWorkerExit关闭线程，且不会接收新任务（addWorker会校验状态）