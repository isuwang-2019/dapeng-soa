package com.github.dapeng.registry.etcd;

import com.coreos.jetcd.watch.WatchEvent;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * desc: watch callback
 *
 * @author hz.lei
 * @since 2018年07月20日 下午4:14
 */
public interface WatchCallback {
    /**
     * 回调
     * <p>
     * //     * @param events event
     */
//    void callback(List<WatchEvent> events);


    void callback();

}
