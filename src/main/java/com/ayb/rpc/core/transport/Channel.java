package com.ayb.rpc.core.transport;
import java.net.InetSocketAddress;

/**
 * 类说明
 *
 * @author fishermen
 * @version V1.0 created at: 2013-5-21
 */

public interface Channel {

    /**
     * get local socket address.
     *
     * @return local address.
     */
    InetSocketAddress getLocalAddress();

    /**
     * get remote socket address
     *
     * @return
     */
    InetSocketAddress getRemoteAddress();

    /**
     * send request.
     *
     * @param request
     * @return response future
     */
    void request(Request request);

    /**
     * open the channel
     *
     * @return
     */
    boolean open();

    /**
     * close the channel.
     */
    void close();

    /**
     * close the channel gracefully.
     */
    void close(int timeout);

    /**
     * is closed.
     *
     * @return closed
     */
    boolean isClosed();

    /**
     * the node available status
     *
     * @return
     */
    boolean isAvailable();

    /**
     * @return
     */
}
