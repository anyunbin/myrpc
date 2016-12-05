package com.ayb.rpc.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yunbinan on 16-8-25.
 */
public class SmsServiceImpl implements SmsService {

    public AtomicInteger count = new AtomicInteger();
    @Override
    public String send(String mobile, String content) {
     /*   try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("result:"+count);
        return count.incrementAndGet()+"";
    }

    @Override
    public String feedback(String msgId) {
        return "success";
    }
}
