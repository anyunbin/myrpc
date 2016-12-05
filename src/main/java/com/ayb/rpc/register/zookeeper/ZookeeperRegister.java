package com.ayb.rpc.register.zookeeper;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.register.AbstractRegister;
import com.ayb.rpc.core.register.NotifyListener;
import com.ayb.rpc.util.Constant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunbinan on 16-9-7.
 */
public class ZookeeperRegister extends AbstractRegister {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperRegister.class);
    private ZooKeeper zooKeeper;

    public ZookeeperRegister(RegisterConfig registerConfig) {
        super(registerConfig);
        try {
            zooKeeper = new ZooKeeper(registerConfig.getRegisterHost() + ":" + registerConfig.getRegisterPort(), 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    logger.info("watch nothing to do");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void doRegister(RegisterConfig registerConfig) {
        try {
            if (zooKeeper.exists(Constant.REGISTER_ROOT, false) == null) {
                zooKeeper.create(Constant.REGISTER_ROOT, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zooKeeper.exists(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath(), false) == null) {
                zooKeeper.create(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            zooKeeper.create(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath() + "/" + registerConfig.getServerHost() + ":" + registerConfig.getServerPort(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            logger.info("doRegister:{}", registerConfig);
        } catch (KeeperException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void doSubscribe(final RegisterConfig registerConfig, final NotifyListener listener) {
        try {
            zooKeeper.getChildren(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath(), new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    logger.info("watchEvent path:{} type:{}", watchedEvent.getPath(), watchedEvent.getType());
                    listener.notify(doDiscover(registerConfig));
                    try {
                        zooKeeper.getChildren(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath(), this, new Stat());
                        logger.info("watch path:{}", Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath());
                    } catch (KeeperException e) {
                        logger.error(e.getMessage(), e);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }, new Stat());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DiscoverServiceConfig> doDiscover(RegisterConfig registerConfig) {
        List<DiscoverServiceConfig> serviceConfigs = new ArrayList();
        try {
            List<String> servers = zooKeeper.getChildren(Constant.REGISTER_ROOT + "/" + registerConfig.getParentPath(), false);
            for (String server : servers) {
                serviceConfigs.add(new DiscoverServiceConfig(server.split(":")[0], Integer.parseInt(server.split(":")[1])));
            }
            logger.info("doDiscover serverConfigs:{}", serviceConfigs);
        } catch (KeeperException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return serviceConfigs;
    }

}
