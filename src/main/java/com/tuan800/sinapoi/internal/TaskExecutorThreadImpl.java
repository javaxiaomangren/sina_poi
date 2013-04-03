package com.tuan800.sinapoi.internal;

import com.tuan800.sinapoi.TaskExecutor;
import com.tuan800.sinapoi.TaskManager;
import com.tuan800.sinapoi.util.Utils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @auth Yang Hua
 * Date: 1/22/13 Time: 6:21 PM
 */
public class TaskExecutorThreadImpl<T> extends Thread implements TaskExecutor<T> {

    private final Logger log = LoggerFactory.getLogger(TaskExecutorThreadImpl.class);

    static PropertiesConfiguration conf = Utils.getConfig() ;
    /*System processor counts*/
    int procs = Runtime.getRuntime().availableProcessors() ;
    /*
    *   a thread safe queue
    *  ArrayBlockingQueue 是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序
    *  LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，
    *  吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
    * */
    private BlockingQueue<T> queue =  new ArrayBlockingQueue<T>(procs*10);
    /**
     *Thread pool
     * @param corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，
     * 即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。
     * 如果调用了线程池的prestartAllCoreThreads方法，线程池会提前创建并启动所有基本线程
     * @param maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。
     * 值得注意的是如果使用了无界的任务队列这个参数就没什么效果
     * @param keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。
     * 所以如果任务很多，并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
     * @param TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），小时（HOURS），分钟（MINUTES），
     * 毫秒(MILLISECONDS)，微秒(MICROSECONDS, 千分之一毫秒)和毫微秒(NANOSECONDS, 千分之一微秒)。
     * @param SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
     * 吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用了这个队列。
     * */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            conf.getInt("executor.corePoolSize"),
            conf.getInt("executor.maximumPoolSize"),
            0,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(){
        @Override
        public boolean offer(Runnable e) {
            try {
                put(e);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return true;
        }
    }) ;

    private volatile boolean _shutdown = false;
    private FetchTask fetchTask = new FetchTask() ;
    private final int fetchCondition = procs ;
    private static TaskManager taskManager = null  ;
    static {
        try {
            taskManager = (TaskManager) Class.forName(conf.getString("executor.taskManagerImpl")).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        _shutdown = false ;
        fetchTask.start();
        this.start();
    }

    @Override
    public void run(){
        while(!_shutdown){
            try {
                if(queue.size()>0){
                    T task =  queue.take();
                    executor.execute(new Execution(task, taskManager));
                    if (queue.size() < fetchCondition) {
                        fetchTask.interrupt() ;
                    }
                }
            } catch (InterruptedException e) {
            }
        }
        executor.shutdown();
        fetchTask.interrupted();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Define A Thread to handle Task
     */
    class Execution extends Thread{
        private final T task ;
        private final TaskManager manager ;
        public Execution(T task, TaskManager manager){
            this.task = task  ;
            this.manager = manager ;
        }
        @Override
        public void run(){
            manager.handle(task);
        }
    }
    /* Define a Thread to fetch task */
    class FetchTask extends Thread{

        @Override
        public void run(){
            while (!_shutdown){
                try{
                    if(queue.size() > fetchCondition){
                        Thread.sleep(Integer.MAX_VALUE);
                    }else{
                        queue.addAll(fetch()) ;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<T> fetch(){
        List<T>  tasks = taskManager.getPersistence().getTasksBySql(conf.getString("taskSql")) ;
        if(tasks.size() == 0){
//            List<String> batch = new  ArrayList<String>() ;
//            batch.add("update");
//             taskManager.getPersistence().executeBatchSql();
            _shutdown = true ;
        }
        log.info("fetch tasks -->" + tasks.size());
        return tasks ;
    }

    @Override
    public void execute(T ... tasks) {
         throw new UnsupportedOperationException("not implemented yet") ;
    }
}
