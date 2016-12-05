package com.ayb.rpc.core.transport;

import com.ayb.rpc.core.config.DiscoverServiceConfig;
import com.ayb.rpc.core.config.ProtocolConfig;
import com.ayb.rpc.core.config.RegisterConfig;
import com.ayb.rpc.core.export.Exporter;
import com.ayb.rpc.core.handler.MessageHandler;
import com.ayb.rpc.core.module.URL;
import com.ayb.rpc.core.refer.Referer;

/**
 *
 */
public interface EndpointFactory {

    /**
     * create remote server
     *
     * @return
     */
    Server getServer(Exporter exporter, RegisterConfig config,ProtocolConfig protocolConfig);

    /**
     * create remote client
     *
     * @return
     */
    Client getClient(DiscoverServiceConfig config, Referer referer,ProtocolConfig protocolConfig);

}
