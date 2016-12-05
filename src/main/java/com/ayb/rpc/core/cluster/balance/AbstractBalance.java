package com.ayb.rpc.core.cluster.balance;

import com.ayb.rpc.core.cluster.Balance;
import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.refer.Referer;
import com.ayb.rpc.core.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yunbinan on 16-9-6.
 */
public abstract class AbstractBalance<T> implements Balance<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractBalance.class);
    protected List<Referer<T>> referers;

    @Override
    public void onRefresh(List<Referer<T>> referers) {
        this.referers = referers;
        logger.info("referes list:{}", referers);
    }

    @Override
    public Referer select(Request request) {
        return doSelect(request);
    }

    @Override
    public Referer<T> getExistingReferer(DiscoverServiceConfig discoverServiceConfig) {
        if (referers != null) {
            for (Referer referer : referers) {
                if (referer.getConfig().equals(discoverServiceConfig))
                    return referer;
            }
        }
        return null;
    }

    public abstract Referer<T> doSelect(Request request);

    public List<Referer<T>> getReferers() {
        return referers;
    }
}
