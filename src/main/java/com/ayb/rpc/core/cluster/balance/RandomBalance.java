package com.ayb.rpc.core.cluster.balance;

import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.Request;

import java.util.Random;

/**
 * Created by yunbinan on 16-8-31.
 */
public class RandomBalance<T> extends AbstractBalance<T> {
    private Random random = new Random();

    @Override
    public Referer<T> doSelect(Request request) {
        if (getReferers() == null || getReferers().size() == 0) {
            return null;
        }
        return referers.get(random.nextInt(referers.size()));
    }
}
