/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dapeng.registry.zookeeper;

import com.github.dapeng.cookie.CookieRule;
import com.github.dapeng.core.RuntimeInstance;
import com.github.dapeng.core.ServiceFreqControl;
import com.github.dapeng.core.Weight;
import com.github.dapeng.core.enums.LoadBalanceStrategy;
import com.github.dapeng.router.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * service information of ZK, including runtime and config
 *
 * @author ever
 * @date 2018/11/16
 */
public class ZkServiceInfo {
    private final String ServiceName;

    /**
     * instances list, always use cow collection
     */
    private List<RuntimeInstance> runtimeInstances;

    /**
     * 路由规则
     */
    private List<Route> routes = new ArrayList<>(16);

    private ServiceFreqControl freqControl = null;

    private List<CookieRule> cookieRules = new ArrayList<>(8);

    public ZkServiceInfo(String serviceName, List<RuntimeInstance> runtimeInstances) {
        this.ServiceName = serviceName;
        this.runtimeInstances = runtimeInstances;
    }

    public List<RuntimeInstance> runtimeInstances() {
        return runtimeInstances;
    }

    /**
     * 根据节点ip以及端口， 找到对应的服务节点
     * @param ip
     * @param port
     * @return
     */
    public RuntimeInstance runtimeInstance(String ip, int port) {
        for (RuntimeInstance runtimeInstance : runtimeInstances) {
            if (runtimeInstance.ip.equals(ip) && runtimeInstance.port == port) {
                return runtimeInstance;
            }
        }

        return null;
    }

    public void routes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> routes() {
        return routes;
    }

    public void cookieRules(List<CookieRule> cookieRules) {
        this.cookieRules = cookieRules;
    }

    public List<CookieRule> cookieRules() {
        return cookieRules;
    }

    public void freqControl(ServiceFreqControl freqControl) {
        this.freqControl = freqControl;
    }

    public ServiceFreqControl freqControl() {
        return freqControl;
    }

    public String serviceName() {
        return ServiceName;
    }

    //～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～
    //                           that's begin  config                              ～
    //                                                                             ～
    //～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～～
    /**
     * processTime zk config
     */
    public Config<Long> processTimeConfig = new Config<>();

    /**
     * timeout zk config
     */
    public Config<Long> timeConfig = new Config<>();
    /**
     * loadBalance zk config
     */
    public Config<LoadBalanceStrategy> loadbalanceConfig = new Config<>();

    /**
     * weight zk config
     */
    public Weight weightGlobalConfig = new Weight();

    public List<Weight> weightServiceConfigs = new ArrayList<>();

    /**
     * config class
     */
    public static class Config<T> {
        public T globalConfig;
        public Map<String, T> serviceConfigs = new HashMap<>();
        public Map<String, T> instanceConfigs = new HashMap<>();
    }
}
