package com.ayb.rpc.core.transport;

/**
 * 类说明
 *
 * @author fishermen
 * @version V1.0 created at: 2013-5-21
 */

public interface Client extends Endpoint {
    /**
     * async send request.
     *
     * @param request
     */
    void heartbeat(Request request);
}
