package com.ayb.rpc.core.transport;

/**
 * @author maijunsheng
 * @version 创建时间：2013-6-14
 */
public interface Future {
    /**
     * cancle the task
     *
     * @return
     */
    boolean cancel();

    /**
     * task cancelled
     *
     * @return
     */
    boolean isCancelled();

    /**
     * task is complete : normal or exception
     *
     * @return
     */
    boolean isDone();

    /**
     * isDone() & normal
     *
     * @return
     */
    boolean isSuccess();

    /**
     * if task is success, return the result.
     *
     * @return
     * @throws Exception when timeout, cancel, onFailure
     */
    Object getValue();

    /**
     * if task is done or cancle, return the exception
     *
     * @return
     */
    Exception getException();

}
