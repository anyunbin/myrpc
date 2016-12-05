package com.ayb.rpc.demo;

/**
 * Created by yunbinan on 16-8-25.
 */
public interface SmsService {
    public String send(String mobile, String content);

    public String feedback(String msgId);
}
