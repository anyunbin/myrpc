package com.ayb.rpc.demo;

import com.ayb.rpc.core.config.ReferConfig;

import java.util.concurrent.*;

/**
 * Created by yunbinan on 16-9-7.
 */
public class ClientDemo {
    public static int count = 0;
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        ReferConfig<SmsService> referConfig = new ReferConfig<SmsService>();
        referConfig.setInterfaceClass(SmsService.class);
        final SmsService smsService = referConfig.getRef();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 20, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10000),new ThreadPoolExecutor.CallerRunsPolicy());
        int i = 0;
        while (true) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread()+"->"+smsService.send(count + "", "c:" + count++));
                }
            });
            //System.out.println(smsService.send(count + "", "c:" + count++));
        }
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread() + "->" + smsService.send(count + "", "c:" + count++));
                }
            }
        }).start();*/

        //LockSupport.park();
    }
}
